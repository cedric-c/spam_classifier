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


def normalize(data):
    return ""




def get_min_max_std(list):
    min = max = None

    for v in list:
        if (min == None or min > v):
            min = v

        if(max == None or max < v):
            max = v

    return (min, max)




def get_column_values(data):
    columns = data[0].keys()
    values = {}

    for col in columns:
        values[col] = []

    for row in data:
        for col in columns:
            values[col].append(row[col])

    return values


def normalize(data):

    keys = data[0].keys()
    all_values = get_column_values(data)

    for record in data:
        for column in keys:

            if(column == 'class'):
                continue

            min, max = get_min_max_std(all_values[column])

            nrmlz = lambda a : (a - min) / (max - min)
            normalized = nrmlz(record[column])
            record[column] = normalized
            # print(record,'\nvalue is ',record[column], 'min max', min, max, 'normalized is', normalized);
    return data


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

print(len(f_ham[:400]), len(f_spam[:400]))

def WriteToFile(filename, data):
    with open(filename, 'w') as f:
        w = csv.DictWriter(f, data[0].keys())
        w.writeheader()
        for i in data:
            w.writerow(i)


all_data = f_spam + f_ham

sampled_data = f_spam[:400] + f_ham[:400]
print(len(sampled_data))

random.shuffle(sampled_data)
values = get_column_values(sampled_data)
normalized_data = normalize(sampled_data)
print(all_data)

print(len(hams), len(spams))

WriteToFile('featured_data_normalized_balanced_actual.csv', sampled_data)
