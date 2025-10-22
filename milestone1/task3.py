import re
import sys
from bs4 import BeautifulSoup
from bs4.element import Comment

filename = sys.argv[1]

with open(filename, 'r', encoding='utf-8') as file:
    html_doc = file.read()

soup = BeautifulSoup(html_doc, 'html.parser')

#3
all_tags = soup.find_all(True)
for _, tag in enumerate(all_tags):
    print(tag)