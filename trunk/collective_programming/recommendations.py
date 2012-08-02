# 2012-7-31 鍚涘悰 weibobee@gmail.com
# 鐢靛奖璇勮鐨勫瓧鍏�{鍚嶅瓧锛歿鐢靛奖鍚嶅瓧锛氳瘎鍒唥}

import math
threshhold = 0.1

# 鏍规嵁娆ф皬璺濈 璁＄畻涓や釜浜�鍦ㄥ涓猰ove涓婄殑璁＄畻鐩镐技搴�锛屾瘡涓�釜浜虹殑n涓猰ovie 鐪嬫垚涓�釜n缁寸┖闂村悜閲�
def sim_distance_euclidean(prefs, person1, person2):
    '''璁＄畻涓や釜浜虹殑娆ф皬璺濈'''
    
    # 濡傛灉鍚嶅瓧涓嶅瓨鍦�
    if not (person1 in prefs) or not (person2 in prefs):
        return 0;

    sum = 0;

    flag = True
    for item in prefs[person1]:
        if item in prefs[person2]:
            score1 = prefs[person1][item]
            score2 = prefs[person2][item]
            sum += math.pow((score1 - score2),2)
            flag = False

    if flag == True:
        return 0;
    return 1/(1 + math.sqrt(sum))


# 璁＄畻涓や釜浜�鐨勫叧浜巑ovie鐨勭浉鍏崇郴鏁�姣忎釜浜虹殑n涓猰ovie鍙互鐪嬫垚movie鐨刵涓娊鏍�
def sim_distance_correlation_score(prefs, person1, person2):

    # 濡傛灉鍚嶅瓧涓嶅瓨鍦�
    if not (person1 in prefs) or not (person2 in prefs):
        return 0;

    # 鎵惧嚭鍏辨湁鐨勪笢瑗�
    share_item = {}
    for item in prefs[person1]:
        if item in prefs[person2]:
            share_item[item] = 1

    if len(share_item) <= 0:
        return 0
    
    # 璁＄畻鍒嗘暟鐨勫拰
    sum1 = sum([prefs[person1][it] for it in share_item])
    sum2 = sum([prefs[person2][it] for it in share_item])    

    
    total_items = len(share_item)
    if total_items == 0:
        return 0;
    # 璁＄畻骞冲潎
    average1 = sum1 / total_items
    average2 = sum2 / total_items

    # print("varage1 = " ,average1)
    # print("average2 = " ,average2)
    
    # x-x鍧囧�
    list1 = [prefs[person1][it1] - average1 for it1 in share_item]           
    list2 = [prefs[person2][it2] - average2 for it2 in share_item]                  

    # print(list1)
    # print(list2)
    
    cov_sum = 0
    d1_sum = 0
    d2_sum = 0
    
    # 璁＄畻鍗忔柟宸�鍜�鏂瑰樊
    for i in range(0,total_items):
        cov_sum += list1[i] * list2[i]
         
    COV = cov_sum / total_items
    
    D1 =  sum( [pow(value1,2)  for value1 in list1] ) / total_items
    D2 =  sum( [pow(value2,2)  for value2 in list2] ) / total_items  

    #print("cov_sum " ,cov_sum)
    #print("cov " ,COV)
    #print("D1 " ,D1)
    #print("D2 ", D2)
    
    # 璁＄畻鐩稿叧绯绘暟
    bellow = (math.sqrt(D1) * math.sqrt(D2))
    if bellow == 0:
        return 0
    
    result = COV / bellow

    return result;


# 浣跨敤sim_fun鎵惧嚭prefs涓笌person鏈�atch鐨勫墠n涓汉
def topNMatch(prefs,person, n = 3 ,sim_fun = sim_distance_euclidean):
        scores = [(sim_fun(prefs,person,other),other) for other in prefs if other != person]
        scores.sort()
        scores.reverse()
        return scores[0:n]
    
    
# 鍚�person 鎺ㄨ崘鏈�n涓猧tem 浣跨敤sim_fun璁＄畻鐩镐技搴�
# 鎺ㄨ崘鐨刬tem鏄�person 娌℃湁鐨�涓庝粬鐩镐技搴︽渶澶х殑鍑犱釜浜�瀵筰tem鍔犳潈璇勪环鍒嗘暟鏈�ぇ鐨勫嚑涓猧tem
def getRecommendation(prefs, person, n = 3 , sim_fun = sim_distance_euclidean):
    global threshhold
    
    # totals 浠ｈ〃瑕佹帹鑽愮殑item
    totals = {}

    # sum 瑕佹帹鑽愮殑item鐨�鐩镐技搴︿箣鍜�
    sims = {}
    
    for other in prefs:
        #濡傛灉鏄嚜宸�缁х画
        if other == person:continue
              
        # 璁＄畻鐩镐技搴�
        sim = sim_fun(prefs,person,other)

        # 濡傛灉鐩镐技搴﹀皬浜庢煇鍊�缁х画
        if sim < threshhold : continue

        # 鎺ㄨ崘person娌℃湁鐪嬭繃鐨刬tem
        for item in prefs[other]:
            # 濡傛灉person鐪嬭繃鐨�pass
            if item  in prefs[person]:
                continue
            
            if not item in totals:
                totals[item] = 0          
            #  other 璇勫垎涔樹互鐩镐技搴�
            totals[item] += prefs[other][item] * sim

            if not item in sims:
                sims[item] = 0           
            # 鐩镐技搴︾殑鍜�
            sims[item] += sim

    #print(totals)
    #print(sims)
    # 姣忎竴涓帹鑽愮殑item 鍒嗘暟鐩稿浜庣浉浼煎害鐨勫姞鏉�
    recommendation = [ (total / sims[item] ,item) for item, total in totals.items()]

    recommendation.sort()
    recommendation.reverse()
    return recommendation[0:n]
            
#  灏嗙墿鍝佷笌浜虹殑浣嶇疆浜ゆ崲
def transformPrefs(prefs):
    result = {}
    for person in prefs:
        for item in prefs[person]:
            result.setdefault(item,{})
            #灏嗙墿鍝佷笌浜虹殑浣嶇疆浜ゆ崲
            result[item][person] = prefs[person][item]
    return result
    
