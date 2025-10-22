import re
import sys
from bs4 import BeautifulSoup
from bs4.element import Comment

filename = sys.argv[1]

with open(filename, 'r', encoding='utf-8') as file:
    html_doc = file.read()

soup = BeautifulSoup(html_doc, 'html.parser')

#5
a_string = soup.find('a', string="Sublime Text Community Documentation")
if a_string:
	print(a_string.find_parent('li'))