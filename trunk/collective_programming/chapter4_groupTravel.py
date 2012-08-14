import math
import random
import time

people = [('Seymour','BOS'),
            ('Franny','DAL'),
            ('Zooey','CAK'),
            ('Walt','MIA'),
            ('Buddy','ORD'),
            ('Les','OMA')]

# laguardia airport in New York
destination = 'LGA'

def getFlightInfoMap(filename):
    flights = {}
    for line in open(filename):
        origin ,dest, depart,arrive,price = line.strip().split(",")
        # key is (origin , dest)
        flights.setdefault((origin,dest),[])
        #flight infomation
        flights[(origin,dest)].append((depart,arrive,price))
    return flights

flights = getFlightInfoMap("schedule.txt")
 
def getminutes(t):
    x = time.strptime(t,"%H:%M")
    return x[3]*60 + x[4]

# r == [1,4,3,2,7,3,6,3,2,4,5,3]
def printschedule(r):
    for d in range((int)(len(r)/2)):
        name = people[d][0]
        origin = people[d][1]
        out = flights[(origin,destination)][r[d]]
        ret = flights[(destination, origin)][r[d+1]]
        
        print("%s, %s, out: %s - %s  %s$   ret: %s - %s %s$" %(name,origin,out[0],out[1], out[2], ret[0], ret[1],ret[2] ))

# 成本函数
# sol =  [1,4,3,2,7,3,6,3,2,4,5,3]
def schedulecost(sol):
    totalprice = 0
    latestarrival = 0
    earliestdep = 24*60
  #  print(sol)
    if sol == None:
        return 99999999
    for d in range(int(len(sol)/2)):
        #get the inbound and outbound  flight
        origin = people[d][1]
        outbound = flights[(origin,destination)][int(sol[d])]
        returnf = flights[(destination, origin)][int(sol[d+1])]
        
        # Total price is the price of all outbound and return flights
        totalprice += (int(outbound[2])+ int (returnf[2]))
        
        #track the latiest arrival andd the earliest departure
        if latestarrival < getminutes(outbound[1]) : latestarrival =  getminutes(outbound[1])
        if earliestdep > getminutes(returnf[0]) : earliestdep = getminutes(returnf[0])
    # every person must wait until the latest person arrived
    # they also must arrive at the same time and wait for their flights
    totalwait = 0
    for d in range((int)(len(sol)/2)):
        #get the inbound and outbound  flight
        origin = people[d][1]
        outbound = flights[(origin,destination)][int(sol[d])]
        returnf = flights[(destination, origin)][int(sol[d+1])]
        
        
        totalwait += latestarrival - getminutes(outbound[1])
        totalwait += getminutes(returnf[0]) - earliestdep
    if latestarrival > earliestdep:
        totalprice+=50
    
    
    return totalprice+totalwait
    # does this solution require an extra day of car rental ? that will be 50$
    
# random  searching optimize
# costf is the CostFunction
# randomly generate 1000 solutions and choose the best one 
def randomoptimize(domain, costf):
    best = 9999999999
    bestr = None
    
    for i in range(1000000) :
        # create a random soluction
        # generate a random number between domain[i][0]) and domain[i][2]
        r = [random.randint(int(domain[j][0]),int(domain[j][1])) for j in range(len(domain))]
        
        # get the cost 
        cost = costf(r)
        
        # compare it to the best one so far
        if cost < best:
            best = cost
            bestr = r
            
        return r;
        
def hillclimb(domain, costf):
    # Create a random solution
    sol = [random.randint(int(domain[j][0]),int(domain[j][1])) for j in range(len(domain))]
    
    # main loop
    
    while True:
        #create list of neighboring soluitons
        neighbors = []
        for j in range(len(domain)):
            # One away in each direction
            if sol[j] > domain[j][0]:
                neighbors.append((sol[0:j]+[sol[j]+1]+sol[j+1:]))
            if sol[j] < domain[j][1]:
                neighbors.append((sol[0:j]+[sol[j]-1]+sol[j+1:]))
        
        # see what the best solution among the neighbors is
        current = costf(sol)
        best = current
        
        for j in range(len(neighbors)):
            cost = costf(neighbors[j])
            if cost < best:
                best = cost
                sol = neighbors[j]
                
        if best == current:
            break;
    return sol

def annealingoptimize(domain,costf,T=10000.0,cool=0.995,step=1):
    # initialize the values randomly
        vec = [random.randint(int(domain[j][0]),int(domain[j][1])) for j in range(len(domain))]
        
        while T>0.1:
            # choose one of the indices
            i = random.randint(0,len(domain)-1)
            
            # Choose a direction to change it
            dir = random.randint(-step,step)
            
            # create a new list with one of the values changed
            vecb = vec[:]
            vecb[i]+=dir
            
            if vecb[i] < domain[i][0] : vecb[i]=domain[i][0]
            elif vecb[i] > domain[i][1] : vecb[i]=domain[i][1]
            
            # Caculate the current cost and the new cost 
            costa = costf(vec)
            costb = costf(vecb)
            p = pow(math.e,(-costb-costa)/T)
                       
            # is it better or does it make the probability
            if costb < costa or float(random.random()) < float(p):
                vec = vecb
            
            # Decrease the temprature
            T =  T * cool
        return vec
    
# Mutation Operation
def mutate(vec,domain,step):
     i = random.randint(1,len(domain)-1)
     if random.random() < 0.5 and vec[i] > domain[i][0]:
         return vec[0:i]+[vec[i]-step] + vec[i+1:]
     elif  vec[i] < domain[i][1]:
         return vec[0:i]+[vec[i]+step] + vec[i+1:]

# crossover Operation
def crossover(r1,r2,domain):
     i = random.randint(1,len(domain)-2)
     return r1[0:i] + r2[i:]  
 
 
def geneticoptionmize(domain, costf, popsize = 50, step=1, mutprod=0.2, elite=0.2, maxiter=100):  
     # build the inital population
    pop=[]
    for i in range(popsize):
        vec=[random.randint(domain[i][0],domain[i][1]) for i in range(len(domain))]
        pop.append(vec)
    
    # how many winners from each generation?
    toplite = int (elite * popsize)
    
    # Main loop
    for i in range(maxiter):
        scores = [(costf(v),v) for v in pop]
        scores.sort()
        ranked=[v for (s,v) in scores]
       # print("ranked size = %d" % len(ranked))
        # start with the pure winners
        pop = ranked[0:toplite]
        
        # add mutated and bred forms of the winners
        
        while len(pop) < popsize:
            if random.random() < mutprod:
                # Mutation
                c = random.randint(0,toplite)
#                print(len(ranked))
#                print("c == %d"  % c)
#                print(ranked[c])
                pop.append(mutate(ranked[c],domain,step))
            else:
                # crossover
               c1 = random.randint(0,toplite)
               c2 = random.randint(0,toplite)
#               print("c1 = %d c2 = %d" %(c1, c2))
#               print(len(ranked))
#               print(ranked[c1])
#               print(ranked[c2])
               if c1 != c2:
                    pop.append(crossover(ranked[c1],ranked[c2],domain))
                    
        print(scores[0][0])
    return scores[0][1]

domainTest = [(0,8)]*2*len(people)
#r = randomoptimize(domainTest,schedulecost)
#r = hillclimb(domainTest,schedulecost)
#r = annealingoptimize(domainTest,schedulecost)
r = geneticoptionmize(domainTest,schedulecost)
printschedule(r)     
cost = schedulecost(r)
print(cost)