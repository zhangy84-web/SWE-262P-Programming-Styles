import re
import sys
from bs4 import BeautifulSoup
from bs4.element import Comment

filename = sys.argv[1]

with open(filename, 'r', encoding='utf-8') as file:
    html_doc = file.read()

soup = BeautifulSoup(html_doc, 'html.parser')

#6
for tag in soup.find_all('b'):
    tag.name = 'blockquote'
with open("blockquote_" + filename, 'w', encoding='utf-8') as outfile:
	outfile.write(soup.prettify())