import re
import sys
from bs4 import BeautifulSoup
from bs4.element import Comment

filename = sys.argv[1]

with open(filename, 'r', encoding='utf-8') as file:
    html_doc = file.read()

soup = BeautifulSoup(html_doc, 'html.parser')

#4
tags_with_id = soup.find_all(id=True)
for _, tag in enumerate(tags_with_id):
    print(tag)