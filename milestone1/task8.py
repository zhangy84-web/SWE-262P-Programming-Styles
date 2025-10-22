import re
import sys
from bs4 import BeautifulSoup
from bs4.element import Comment

filename = sys.argv[1]

with open(filename, 'r', encoding='utf-8') as file:
    html_doc = file.read()

soup = BeautifulSoup(html_doc, 'html.parser')

#8
target_tag = soup.find('p')
if target_tag:
    new_comment = Comment(" START OF MAIN CONTENT SECTION ")
    target_tag.insert_before(new_comment)
    print(target_tag.parent.prettify())