import xml.etree.ElementTree as etree

tree = etree.parse('test.xml')
root = tree.getroot()
print(root)
for child in root:
    print(child)
    print(child.attrib)
    print('\n')
