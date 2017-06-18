# connection details for port
import os
from starbase import Connection

c = Connection(port = 20550)

# hbase database model
t = c.table('mchoenron')
t.drop()
t.create('user', 'address', 'date', 'body')

# fill in the database
rowcnt = 0
root = '/home/public/course/enron/'
names = os.listdir(root)
for name in names:
    name_path = os.path.join(root, name)
    emails = os.listdir(name_path)
    
    for email in emails:
        path = os.path.join(name_path, email)
        with open(path) as f:
            lines = f.readlines()

    	   # retrieving date components and email sender and recipient
        date = lines[1].split(' ')
        day = date[2]
        mon = date[3]
        year = date[4]
        time = date[5]

        email_from = lines[2].split(' ')[1]
        email_to = lines[3].split(' ')[1]

        # retrieve the email content by first getting to the row 
        # where X- starts and then iterating until there are no more X-
        # this will be where the body begins and where code breaks out of the for loop
        body = False
        for idx, line in enumerate(lines):
            if not body:
                if line[:2] == 'X-':
                    body = True
            else:
                if line[:2] != 'X-':
                    body_start = idx
                    break

        content=""
        for i in range(body_start, len(lines)):
            content += lines[i]
            
        # insert row which is just the row string and data dictionary, increment rowcnt
        data = {
            'user': {'user': name},
            'address': {'from': email_from, 'to': email_to},
            'date': {'day': day, 'month': mon, 'year': year, 'time': time},
            'body': {'body': content}
        }
        t.insert('row' + str(rowcnt), data)
        rowcnt += 1
 
# write all emails of chosen user to text file
chsn_user = 'mCarson'
f = open('Cho_1_user.txt','wb')
records = ''
for row in range(rowcnt):
    record = t.fetch('row' + str(row), ['body', 'user'])
    if record['user']['user'] == chsn_user:
        f.write( record['body']['body'])
        f.write('\n')
        f.write('-----------------------------------------------------------------------------')
        f.write('\n')
f.close()

# write all emails from particular month and year to text file
chsn_mon = 'Oct'
chsn_year = '2001'
f = open('Cho_1_mon.txt','wb')
for row in range(rowcnt):
    record = t.fetch('row' + str(row), ['body', 'date'])
    if record['date']['month'] == chsn_mon and record['date']['year']==chsn_year:
        f.write(record['body']['body'])
        f.write('\n')
        f.write('-----------------------------------------------------------------------------')
        f.write('\n')
f.close()

### write all emails from particular month, year, and user to text file
chsn_user = 'mCarson'
chsn_mon = 'Oct'
chsn_year = '2001'
f = open('Cho_1_user_mon.txt','wb')
for row in range(rowcnt):
    record = t.fetch('row' + str(row), ['user', 'body', 'date'])
    if record['date']['month'] == chsn_mon and record['date']['year']==chsn_year and record['user']['user'] == chsn_user:
        f.write(record['body']['body'])
        f.write('\n')
        f.write('-----------------------------------------------------------------------------')
        f.write('\n')
f.close()