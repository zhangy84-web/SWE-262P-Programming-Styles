import re
import sys
from bs4 import BeautifulSoup
from bs4.element import Comment

filename = sys.argv[1]

with open(filename, 'r', encoding='utf-8') as file:
    html_doc = file.read()

soup = BeautifulSoup(html_doc, 'html.parser')

#7
for p_tag in soup.find_all('p'):
    # Treat the tag like a dictionary to add or replace the 'class' attribute
    # Setting the value to 'test' overwrites any existing class(es).
    p_tag['class'] = 'test'
with open("ptest_" + filename, 'w', encoding='utf-8') as outfile:
	outfile.write(soup.prettify())