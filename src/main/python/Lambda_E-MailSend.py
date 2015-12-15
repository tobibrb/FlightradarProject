__author__ = 'Christ'
from __future__ import print_function
from boto3.dynamodb.conditions import Key, Attr
import json
import os
import os.path
import sys
import xml.etree.ElementTree as ET


root = os.environ["LAMBDA_TASK_ROOT"]
sys.path.insert(0, root)
import boto3 #should grab boto3 from included zip first

def lambda_handler(event,context):
    print('GO!')
    s3 = boto3.resource('s3',"us-east-1", aws_access_key_id = "AKIAIRNXAZSNX6A4WKPA", aws_secret_access_key = "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")
    s3client= boto3.client('s3',"us-east-1", aws_access_key_id = "AKIAIRNXAZSNX6A4WKPA", aws_secret_access_key = "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")
    dbclient = boto3.client('dynamodb')
    dynamodb = boto3.resource('dynamodb')
    table = dynamodb.Table('Flightdata')

    bucketname = 'flightradaremail'
    bucket = s3.Bucket(bucketname)
    for key in bucket.objects.all():
        #response ist ein s3Objekt
        response = s3client.get_object(Bucket=bucketname, Key=key.key, ResponseContentType="text/xml")
        #Data bekommt einen String aus einem Stream, welcher im Body von 'response' liegt
        data = response["Body"].read();
        #root ist das element <emailListVo> man liest es aus 'data' aus.
        #print(data)
        root = ET.fromstring(data)
        #man kann auf die einezelnen Knoten zugreifen wie bei einem Mehrdimensionalen Array
        #siehe print hier bekommt man nun die Email Adresse vom ersten Element.
        #print(root[0][0][0][0].text)
        #print(root[0][0][0][1].text)
        #for flughafen in root[0][0][0]:
        #    print (flughafen.text)
        #for adress in root.iter('email'):
        #    print (adress.text)

        #print('-----')
        for email in root[0]:
            text = ''
            for flughafen in email[0]:
                print('flughafen: '+flughafen.text)
                try:

                    response = table.scan(FilterExpression=Attr('Start').eq(flughafen.text))
                    items = response['Items']

                    #print(items[0])

                    text += 'Von '+flughafen.text+' startet/ist gestartet:\n'
                    for flug in items:
                        text += 'Flug '+flug['FlightNumber']+' nach '+flug['Destination']+'\n'
                    #=========================================================================================
                    text += '\n\n'
                    response = table.scan(FilterExpression=Attr('Destination').eq(flughafen.text))
                    items = response['Items']

                    #print(items[0])
                    text += 'Nach '+flughafen.text+' fliegt/ist angekommen:\n'
                    for flug in items:
                        text += 'Flug '+flug['FlightNumber']+' nach '+flug['Start']+'\n'

                    text+='\n\n\n'
                except Exception, e:
                    print (e)


            print(text)
            print(email[1].text)
            try:
                send(adress=email[1].text, subject='Infos zu Ihrem Lieblings-Flughafen!!!', text=text)
            except Exception, e:
                    print (e)
    #send(adress='flightradartu@gmail.com', subject='test', text='text')


def send(adress, subject, text):
    eClient = boto3.client('ses', "us-east-1", aws_access_key_id = "AKIAJTQQ3B37G5IQSTIA", aws_secret_access_key = "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")
    response = eClient.send_email(
        Source='colin.christ303@gmail.com',
        Destination={
            'ToAddresses': [
                adress,
            ]
        },
        Message={
            'Subject': {
                'Data': subject
            },
            'Body': {
                'Text': {
                    'Data': text
                },
            }
        },

    )