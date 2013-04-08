import re
def plural(noun):
    '''取得复数形式  
    '''
    if re.search('[sxz]$',noun):
        return re.sub('$','es',noun)
        
    elif re.search('[^aeioudgkprt]h$',noun):
        return re.sub('$','es',noun)
    
    elif re.search('[^aeiou]y$',noun):
        return re.sub('y$','ies',noun)
    
    else:
        return noun+'s'
    
def build_match_and_apply_functions(pattern,search,replace):
    def matches_rule(word):
        return re.search(pattern,word)
    def apply_rule(word):
        return re.sub(search,replace,word)
    return (matches_rule,apply_rule)

pattern = \
(
    ('[sxz]$',           '$',  'es'),  
    ('[^aeioudgkprt]h$', '$',  'es'), 
    ('(qu|[^aeiou])y$',  'y$', 'ies'),  
    ('$',                '$',  's')
)

rules = [build_match_and_apply_functions(pattern,search,replace) for (pattern,search,replace) in pattern ]
def plural1(noun):
    for matches_rule, apply_rule in rules:
        if(matches_rule(noun)):
            return apply_rule(noun)
        
rules1=[]
with open('rules.txt',encoding='utf-8') as pattern_file:
    for line in pattern_file:
        #print(line)
        (pattern,search,replace) = line.split(None,3)
        rules1.append(build_match_and_apply_functions(pattern,search,replace) )

def plural2(noun):
    for matches_rule, apply_rule in rules:
        if(matches_rule(noun)):
            return apply_rule(noun)
        
if __name__=='__main__':
    print(plural2('watch'))
