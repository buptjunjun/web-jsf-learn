# 2012-7-31 君君 weibobee@gmail.com
# 电影评论的字典 {名字：{电影名字：评分}}

import math
threshhold = 0.1

# 根据欧氏距离 计算两个人 在多个move上的计算相似度 ，每一个人的n个movie 看成一个n维空间向量
def sim_distance_euclidean(prefs, person1, person2):
    '''计算两个人的欧氏距离'''
    
    # 如果名字不存在
    if not (person1 in prefs) or not (person2 in prefs):
        return 0;

    sum = 0;

    for item in prefs[person1]:
        if item in prefs[person2]:
            score1 = prefs[person1][item]
            score2 = prefs[person2][item]
            sum += math.pow((score1 - score2),2)

    return 1/(1 + math.sqrt(sum))


# 计算两个人 的关于movie的相关系数 每个人的n个movie可以看成movie的n个抽样
def sim_distance_correlation_score(prefs, person1, person2):

    # 如果名字不存在
    if not (person1 in prefs) or not (person2 in prefs):
        return 0;

    # 找出共有的东西
    share_item = {}
    for item in prefs[person1]:
        if item in prefs[person2]:
            share_item[item] = 1

    if len(share_item) <= 0:
        return 0
    
    # 计算分数的和
    sum1 = sum([prefs[person1][it] for it in share_item])
    sum2 = sum([prefs[person2][it] for it in share_item])    

    
    total_items = len(share_item)
    
    # 计算平均
    average1 = sum1 / total_items
    average2 = sum2 / total_items

    # print("varage1 = " ,average1)
    # print("average2 = " ,average2)
    
    # x-x均值
    list1 = [prefs[person1][it1] - average1 for it1 in share_item]           
    list2 = [prefs[person2][it2] - average2 for it2 in share_item]                  

    # print(list1)
    # print(list2)
    
    cov_sum = 0
    d1_sum = 0
    d2_sum = 0
    
    # 计算协方差 和 方差
    for i in range(0,total_items):
        cov_sum += list1[i] * list2[i]
         
    COV = cov_sum / total_items
    
    D1 =  sum( [pow(value1,2)  for value1 in list1] ) / total_items
    D2 =  sum( [pow(value2,2)  for value2 in list2] ) / total_items  

    #print("cov_sum " ,cov_sum)
    #print("cov " ,COV)
    #print("D1 " ,D1)
    #print("D2 ", D2)
    
    # 计算相关系数
    result = COV / (math.sqrt(D1) * math.sqrt(D2))

    return result;


# 使用sim_fun找出prefs中与person最match的前n个人
def topNMatch(prefs,person, n = 3 ,sim_fun = sim_distance_euclidean):
        scores = [(sim_fun(prefs,person,other),other) for other in prefs if other != person]
        scores.sort()
        scores.reverse()
        return scores[0:n]
    
    
# 向 person 推荐最多n个item 使用sim_fun计算相似度
# 推荐的item是 person 没有的,与他相似度最大的几个人 对item加权评价分数最大的几个item
def getRecommendation(prefs, person, n = 3 , sim_fun = sim_distance_euclidean):
    global threshhold
    
    # totals 代表要推荐的item
    totals = {}

    # sum 要推荐的item的 相似度之和
    sims = {}
    
    for other in prefs:
        #如果是自己 继续
        if other == person:continue
              
        # 计算相似度
        sim = sim_fun(prefs,person,other)

        # 如果相似度小于某值 继续
        if sim < threshhold : continue

        # 推荐person没有看过的item
        for item in prefs[other]:
            # 如果person看过的 pass
            if item  in prefs[person]:
                continue
            
            if not item in totals:
                totals[item] = 0          
            #  other 评分乘以相似度
            totals[item] += prefs[other][item] * sim

            if not item in sims:
                sims[item] = 0           
            # 相似度的和
            sims[item] += sim

    #print(totals)
    #print(sims)
    # 每一个推荐的item 分数相对于相似度的加权
    recommendation = [ (total / sims[item] ,item) for item, total in totals.items()]

    recommendation.sort()
    recommendation.reverse()
    return recommendation[0:n]
            
                
        
    
