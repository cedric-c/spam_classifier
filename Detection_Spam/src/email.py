# Content-Type: (text/plain|text/html); charset=(.+?)\r?\nContent-Transfer-Encoding: (.+?)\r?\n


from os import listdir
import re
import csv, random

hamDir = "data/ham-and-spam-dataset/ham"
spamDir = "data/ham-and-spam-dataset/spam"

exp = r'(Content-Type: (text/plain|text/html); charset=(.+?)\r?\nContent-Transfer-Encoding: (.+?)\r?\n)'
exp = r'(Content-Type:)'
exp = r'(Content-Type:)(\n|.*)*'

content_type_pattern = re.compile(exp, re.DOTALL)

r_paragraph = r'\n\n'
p_paragraph = re.compile(r_paragraph, re.DOTALL | re.MULTILINE)


r_html_tag = r'(<[a-z]*>)'
p_html_tag = re.compile(r_html_tag)

hamFilenames = [f for f in listdir(hamDir)]
spamFilenames = [f for f in listdir(spamDir)]
# print(hamFilenames)
iso = "ISO-8859-1"
latin = "latin-1"

def read_parseable_files(obs):
    files = {}
    directory, filenames = obs
    total = found = notFound = couldntRead = 0
    for f in filenames:
        path = directory +"/" + f
        with open(path, 'r', encoding=latin) as fOpen:
            total += 1
            try:
                data = fOpen.read()
                pattern = re.compile(exp)
                a = re.search(r'(Content-Type: (text/plain|text/html); charset=(.+?)\r?\nContent-Transfer-Encoding: (.+?)\r?\n)', data)
                b = pattern.search(data)
                if(b != None):
                    found += 1
                    # print(path)
                    files[f] = path
                else:
                    notFound += 1
                # print(a,"\n", b)

            except:
                # print("Couldn't read " + f, fOpen.encoding)
                couldntRead += 1
            # break
    print('Total', total,
          'found', found,
          'notFound', notFound,
          'unreadable', couldntRead,
          'ratio', found/total,
          )
    return files



hams = read_parseable_files((hamDir, hamFilenames))
spams = read_parseable_files((spamDir, spamFilenames))

def GetMaxWordLength(paragraphs):
    currentMax = 0;
    for w in paragraphs:
        if len(w) > currentMax:
            currentMax = len(w)
    return currentMax


def GetAverageCharactersPerPara(paragraphs):
    length = 0;
    for paragraph in paragraphs:
        length += len(paragraph)

    return length/len(paragraphs)


# meta
# . ^ $ * + ? { } [ ] \ | ( )
def find_features(ham_or_spam, cls):
    all_data = []
    for filename, path in ham_or_spam.items():
        data = {}
        with open(path, 'r', encoding=latin) as file:
            content = file.read()
            part = re.search(content_type_pattern, content)
            # print(part, part.start())
            header = content[:part.start()]
            body = content[part.start():]
            num_chars = len(body)
            paragraphs = re.split(p_paragraph, body) # COUNT THIS
            html_tags = re.split(p_html_tag, body)
            html_tags2 = re.findall(p_html_tag, body)
            mailLinks = re.split(r'<mailto:', body)
            nonWordBoundaries = re.findall(r'\B', body)
            wordBoundaries = re.findall(r'\b', body)
            whiteSpace = re.findall(r'\s+', body)
            wrds = re.split(r'\s+', body)
            words = body.split(' ');
            digits = re.findall(r'\d+', body)
            avgCharsPerParagraph = GetAverageCharactersPerPara(paragraphs)

            upper_chars = sum(1 for c in body if c.isupper())
            lower_chars = sum(1 for c in body if c.islower())

            data["paragraphs"] = len(paragraphs)
            data["avg_chars_paragraph"] = avgCharsPerParagraph
            data["word_boundary_ratio"] = len(wordBoundaries) / len(words)
            data["n_word_boundary_ratio"] = len(nonWordBoundaries) / len(words)
            data["whitespace_ratio"] = len(whiteSpace) / len(words)
            data["words"] = len(words)
            data["max_word_len"] = GetMaxWordLength(words)
            data["digits"] = len(digits)
            data["upper_ratio"] = upper_chars / num_chars
            data["lower_ratio"] = lower_chars / num_chars
            data["upper_lower_ratio"] = upper_chars / lower_chars
            data["avg_digit_length"] = GetAverageCharactersPerPara(digits)
            data["links"] = len(mailLinks)



            # average words per paragraph
            # print('\n\nnonWordBoundaries ',nonWordBoundaries,
            #       '\n\nwordBoundaries ',wordBoundaries,
            #       '\n\nwhiteSpace ',whiteSpace ,
            #       '\n\nwords ',words,
            #       '\n\nwrds ', wrds,
            #       '\n\ndigits', digits,
            #       '\n\navgCharsPerParagraph ',avgCharsPerParagraph)
            # print('paragraphs',len(paragraphs), 'html tags', len(html_tags))
            # print(len(mailLinks))

            data["class"] = cls
            all_data.append(data)
    return all_data



hello = ['12345', '12345', '']


# print(hamFiles)
f_spam = find_features(spams, "spam")
f_ham = find_features(hams, "ham")



def WriteToFile(filename, data):
    with open(filename, 'w') as f:
        w = csv.DictWriter(f, data[0].keys())
        w.writeheader()
        for i in data:
            w.writerow(i)


all_data = f_spam + f_ham

random.shuffle(all_data)
WriteToFile('featured_data.csv', all_data)

# with open(spamDir+"/0039.256602e2cb5a5b373bdd1fb631d9f452", 'r', encoding=iso) as o:
#     print(o.encoding)
#     contents = o.read();
#     print(contents)
#     print('\n\n\n\n','replaced','\n\n\n\n')
#     print(contents.replace('<BR>', '\n'))