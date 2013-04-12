from lxml import etree

tree = etree.parse('test.xml')
root = tree.getroot()
print(root)
for child in root:
    print(child)
    print(child.attrib)
    print('\n')
    
print(tree.findall('//{http://www.w3.org/2005/Atom}title'))
print(tree.findall('{http://www.w3.org/2005/Atom}*[@href]'))
#namespace
ns = '{http://www.w3.org/2005/Atom}' 
print(tree.findall('//{}author[{}uri]'.format(ns,ns)))

nsmap={'atom':'http://www.w3.org/2005/Atom'}
entries = tree.xpath('//atom:category[@term="html"]/..',namespaces=nsmap);
print(entries)
print(entries[0].xpath('./atom:title/text()',namespaces=nsmap))


#生成xml