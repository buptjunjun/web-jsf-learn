# # 2012-7-31 君君 weibobee@gmail.com
#测试 推荐系统
import recommendation.recommendations as r
critics = {"andy": {"ghost rider":1.5, "2012":2.5,"star war":3.5,"star war":3,"qing se":5,"haha":3.5},
           "xiaolan": {"ghost rider":1, "2012":3,"star war":3.0},
           "wanhai": {"ghost rider":3.0, "2012":3.5,"star war":2.0,"aa":3,"qing se":1,"haha":3.5},
           "qianqian": {"ghost rider":1, "2012":2,"star war":3.5,"bb":3,"hong lou meng":2.5,"haha ":2.5},
           "xiaohai": {"ghost rider":2.0, "2012":1,"star war":2.0, "bb":4.5,"xiao bing zhang ga":1,"xixi ":3},
}
list1 = [1,2,3]
list2 = [2,3,4]
list3 = []
  
print(list3)
# 测试欧氏距离
sim_score1 = r.sim_distance_euclidean(critics,"andy","xiaolan")
sim_score2 = r.sim_distance_euclidean(critics,"andy","wanhai")
print("sim_score1 = %f" % sim_score1)
print("sim_score2 = %f" % sim_score2)

# 测试 相关系数
corrlate_score = r.sim_distance_correlation_score(critics,"andy","xiaolan")
print("sim_score = %f" % corrlate_score)

# 测试 topNMatch
topMatch = r.topNMatch(critics, "xiaolan", 2, r.sim_distance_correlation_score)
print(topMatch)

# 测试推荐item

topRecom = r.getRecommendation(critics, "xiaolan",2)
print(topRecom)
