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

special_characters = ['<', '>', '?', '\\', '*', '&', '^', '%', '*', '@', '!', '$']

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
            nrmlz = lambda a: (a - min) / (max - min)
            normalized = nrmlz(record[column])
            record[column] = normalized
            # print(record,'\nvalue is ',record[column], 'min max', min, max, 'normalized is', normalized);
    return data


def syllable_count(word):
    """
    source: # https://stackoverflow.com/questions/46759492/syllable-count-in-python
    :param word: The word you would like the syllable count for
    :return: The number of syllables in the given word
    """
    word = word.lower()
    count = 0
    vowels = "aeiouy"
    if word[0] in vowels:
        count += 1
    for index in range(1, len(word)):
        if word[index] in vowels and word[index - 1] not in vowels:
            count += 1
    if word.endswith("e"):
        count -= 1
    if count == 0:
        count += 1
    return count


def average_syllable_count(body):
    """
    Counts the average number of syllables per word.
    :param body:
    :return:
    """
    sum_syllables = 0
    for word in body:
        sum_syllables += syllable_count(word)
    return sum_syllables / len(body)


def number_complex_words(body):
    """
    A complex word is a word with 2 or more syllables
    :param body:
    :return:
    """
    return len([word for word in body if syllable_count(word) >= 2])


def is_simple_word(word):
    sufixes = ['acy', 'al', 'ance', 'ence', 'dom', 'er', 'or', 'ism', 'ist', 'ity', 'ty', 'ment',
               'ness', 'ship', 'sion', 'tion', 'ate', 'en', 'ify', 'fy', 'ise', 'ize', 'able', 'ible',
               'al', 'esque', 'ful', 'ic', 'ical', 'ious', 'ous', 'ish', 'ive', 'less', 'y', 'ly',
               'ward', 'wards', 'wise', 'itis', 'pathy', 'penia', 'tomy', 'otomy', 'logy', 'lysis', 'osis', 'centisis']

    prefixes = ['ante', 'anti', 'circum', 'co', 'de', 'dis', 'em', 'epi', 'ex', 'extra', 'fore',
                'homo', 'hyper', 'il', 'im', 'infra', 'inter', 'macro', 'micro', 'mid',
                'mis', 'mono', 'non', 'omni', 'para', 'post', 'pre', 're', 'semi', 'sub',
                'super', 'therm', 'trans', 'tri', 'un', 'uni', 'en', 'in', 'ir', 'intra',
                'endo', 'eu', 'hemi', 'hetero', 'poly', 'tetra', 'iso', 'di', 'hypo', 'peri',
                'pro', 'con', 'ad', 'auto', 'pan', 'dia', 'neo', 'ab', 'bi', 'retro', 'tele', 'be', 'an']

    for s in sufixes:
        if word.startswith(s):
            return True

    for p in prefixes:
        if word.endswith(p):
            return True

    return False


def number_simple_words(body):
    """
    Counts the number of simple words in a body. A simple word is one that doesn't have affixes or prefixes.

    See: https://www.myenglishteacher.eu/blog/prefixes-suffixes-list/
    :param body:
    :return:
    """
    simple_word_count = 0
    for word in body:
        if is_simple_word(word):
            simple_word_count += 1

    return simple_word_count


def CountOccurrences(data, list):
    s = 0
    for i in list:
        s += data.count(i)
    return s


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
            paragraphs = re.split(p_paragraph, body)  # COUNT THIS
            html_tags = re.split(p_html_tag, body)
            html_tags2 = re.findall(p_html_tag, body)
            mailLinks = re.split(r'<mailto:', body)
            num_lines = sum(1 for line in body)
            nonWordBoundaries = re.findall(r'\B', body)
            words_1_to_3 = re.findall(r'\w{1,3}', body)
            words_6_plus = re.findall(r'\w{6,}', body)
            wordBoundaries = re.findall(r'\b', body)
            whiteSpace = re.findall(r'\s+', body)
            wrds = re.split(r'\s+', body)
            words = body.replace('\n', '').split(' ')
            words = [value for value in words if value != '']  # remove all occurences of ''
            digits = re.findall(r'\d+', body)
            avgCharsPerParagraph = GetAverageCharactersPerPara(paragraphs)
            sentences = re.split(r'[.!?]+', body.replace('\n', ''))

            upper_chars = sum(1 for c in body if c.isupper())
            lower_chars = sum(1 for c in body if c.islower())

            data["paragraphs"] = len(paragraphs)
            data["avg_chars_paragraph"] = avgCharsPerParagraph
            data["word_boundary_ratio"] = len(wordBoundaries) / len(words)
            data["n_word_boundary_ratio"] = len(nonWordBoundaries) / len(words)
            data["whitespace_ratio"] = len(whiteSpace) / len(words)
            data["num_white_space"] = len(whiteSpace)
            data["words"] = len(words)
            data["max_word_len"] = GetMaxWordLength(words)
            data["digits"] = len(digits)
            data["upper_ratio"] = upper_chars / num_chars
            data["lower_ratio"] = lower_chars / num_chars
            data["upper_lower_ratio"] = upper_chars / lower_chars
            data["avg_digit_length"] = GetAverageCharactersPerPara(digits)
            data["links"] = len(mailLinks)
            data["special_characters"] = CountOccurrences(body, special_characters)
            data["comma_count"] = CountOccurrences(body, [','])
            data["period_count"] = CountOccurrences(body, ['.'])
            data["single_quote_count"] = CountOccurrences(body, ['\''])
            data["semi_colon_count"] = CountOccurrences(body, [';'])
            data["words_len_1-3"] = len(words_1_to_3)
            data["words_len_6+"] = len(words_6_plus)
            data["num_lines"] = num_lines
            x24 = num_sentences = len(sentences)
            data["num_sentences"] = num_sentences

            x25 = avg_syllables = average_syllable_count(words)
            data['avg_syllables'] = avg_syllables

            x22 = num_complex_words = number_complex_words(words)
            data['num_complex_words'] = num_complex_words

            x23 = num_simple_words = number_simple_words(words)
            data['num_simple_words'] = num_simple_words

            # tool for measure of readability of text, estimate of level of education required to read text
            print(num_sentences, num_simple_words, words)

            try:
                x26 = fog_index = 0.4 * (num_complex_words / num_sentences) + 100 * (num_complex_words / num_simple_words)
            except:
                x26 = fog_index = 0
            finally:
                data['fog_index'] = fog_index

            # measure of textual difficulty (Flesch Reading Ease Score)
            flesch_score = 206.835 * (x23 / x24) - 84.6 * x25
            data['flesch'] = flesch_score
            # print(body)
            # body_cleaned = body.replace('\n', '')
            # split_body_cleaned = re.split(r'[.!?]+', body_cleaned)
            # split_body = re.split(r'[.!?]+', body)
            # print('len(split_cleaned): %d len(dirty): %d' % (len(split_body_cleaned), len(split_body)) )


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

WriteToFile('featured__new_features_4.csv', sampled_data)
