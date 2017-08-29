import googlemaps
from datetime import datetime
import time
import json
import sys
from resturant import Resturant
from distance import Distance


WIDTH = 800
HEIGHT = 500

argv_len = len(sys.argv)
print 'Number of arguments:', argv_len, 'arguments.'

args_lst = sys.argv[1: argv_len]

user_lst = list()
origins_lst = list()

cnt = 0
for arg in args_lst:
	if (cnt % 2 == 0):
		user_lst.append(arg)
	else:
		origins_lst.append(arg)
	cnt = cnt + 1

print user_lst
print origins_lst

x = 0
y = 0
for origin in origins_lst:
	origin_arr = origin.split(',', 1)
	x += float(origin_arr[0])
	y += float(origin_arr[1])

x = x / len(origins_lst)
y = y / len(origins_lst)
print "average x = ", x, "y = ", y

x_left_bound = x - 0.0300
x_right_bound = x + 0.0300
y_up_bound = y + 0.0300
y_down_bound = y - 0.0300

# check origins lst
print 'Origins List:', str(origins_lst)

# read LasVegas.json
with open('LasVegas_Restaurants_withID.json') as data_file:    
    data = json.load(data_file)
# print "\n", str(data[0])

#iterate through the list of map
restur_list = list()

restur_dict = {}
for line in data:
	restur = Resturant(line["business_id"], line["name"], line["full_address"], 
		line["latitude"], line["longitude"], line["stars"])
	# restur_list.append(restur)
	restur_dict[line["full_address"]] = restur

# filter resturant by coordinate
filtered_restur_list = list() # the object of resturant
destinations = list() # the addresss of destinations
for key, restur in restur_dict.items():
	lati = restur.latitude
	longti = restur.longtitude
	if lati > x_left_bound and lati < x_right_bound and longti > y_down_bound and longti < y_up_bound:
		filtered_restur_list.append(restur) 
		coordinate = str(restur.latitude) + ',' + str(restur.longtitude)
		destinations.append(coordinate) 


# for desti in destinations:
# 	print desti

print "number of resturant after filter: ", len(filtered_restur_list)

# start using google API
# gmaps = googlemaps.Client(key='AIzaSyCVFTZ8PbWr1h8t94ZVjv3HS6l13UKdcbQ')
gmaps = googlemaps.Client(key='AIzaSyD4TpOXZHiLHtMQlEPestqLbRMNO_1yo5w')
# gmaps = googlemaps.Client(key='AIzaSyDyzXhN2J7fM3wXH8aYoPBTq4a5BsBi_30')



# two user's location
origins = origins_lst
# origins = ["Avenue of the Stars, Los Angeles, CA", "University of California, Los Angeles"] # origins = origins_lst

# two resturant's location
# print str(filtered_restur_list)

# get distance matrix (dictionary)

# print destinations
# print "\n"

big_map = list()
count = 0
turn = 0
for i in filtered_restur_list:
	count += 1
	if count == 30:
		count = 0
		matrix = gmaps.distance_matrix(origins, destinations[turn*30: (turn+1)*30])
		# print "test 30 data: ", len(destinations[turn*30: (turn+1)*30])
		big_map.append(matrix)
		turn += 1

if count > 0:
	# print "test 30 data: ", len(destinations[turn*30: turn*30+count])
	matrix = gmaps.distance_matrix(origins, destinations[turn*30: turn*30+count])
	big_map.append(matrix)

print "length of big map = ", len(big_map)

# matrix = gmaps.distance_matrix(origins, destinations)

#encode dictionary 'matrix' into json format, and save as a file
with open('json_matrix.json', 'w') as outfile:
	json.dump(big_map, outfile, indent=4, sort_keys=True, separators=(',', ':'))

#get each resturant's 'distanct'
# read LasVegas.json
# with open('json_matrix.json') as data_file: # could be deleted  
#     google_data = json.load(data_file)


restur_distant_list = list() # a list of objects of Distance
num = 0 #num of filtered resturant
for map_50 in big_map:
	for i in range(0,len(map_50["destination_addresses"])):
		dist_max = 0
		for j in range(0,len(map_50["origin_addresses"])):
			dist_max = max(dist_max, map_50["rows"][j]["elements"][i]["distance"]["value"])
		# print "dist_max = ", dist_max
		restur_distant = Distance(filtered_restur_list[num].Id, filtered_restur_list[num].name, map_50["destination_addresses"][i], dist_max, filtered_restur_list[num].star)
		restur_distant_list.append(restur_distant)
		num += 1

# sort restur_distant_list with distance min -> max
restur_distant_list = sorted(restur_distant_list, key=lambda restur: restur.distance)   # sort by distance

# check the order of restur_distant_list
# for restur_distant in restur_distant_list:
# 	print "distance:", restur_distant.distance, " star:", restur_distant.star, " ", restur_distant.name

# create <resturant_id, user_id> key
argv_len = (argv_len - 1) / 2
# key_pair_lst = list()
with open('las_review.json') as data_file: # could be deleted  
    restur_rate = json.load(data_file)

for restur_distant in restur_distant_list:
	sum_star = 0.0
	for i in range(0, argv_len):
		key = restur_distant.Id + "," + user_lst[i]
		# print key
		if (restur_rate.get(key) != None):
			sum_star += restur_rate.get(key)
	average_star = sum_star / argv_len
	restur_distant.star = average_star
	print "Restur: ", restur_distant.Id, " star: ", restur_distant.star

# star filter to get top10_recommand_resturant
print "\nFilter out resturant:", 
top10_recommand_resturant = list()
for restur_distant in restur_distant_list:
	if restur_distant.star >= 3.70:
		top10_recommand_resturant.append(restur_distant)

print "\nTop 10 resturants recommanded:\n", 
top10_recommand_resturant = top10_recommand_resturant[:10]
# for restur_distant in top10_recommand_resturant:
# 	print "distance:", restur_distant.distance, " star:", restur_distant.star, "resturant ID:", restur_distant.Id, " ", restur_distant.name


# read review_rating_dict.json
with open('review_rating_dict.json') as data_file:    
    data_nlp = json.load(data_file)

for restur_distant in top10_recommand_resturant:
	restur_distant.nlp_score = data_nlp[restur_distant.Id]
	print "distance:", restur_distant.distance, " name:", restur_distant.name, " star:", restur_distant.star, " nlp_score: ", restur_distant.nlp_score, "resturant ID:", restur_distant.Id


######################## GUI part #########################
# users location
coordinates = list()
for restur_distant in top10_recommand_resturant:
	coordinates.append(restur_distant.coordinate)
# coordinates = ["1106 S 3rd St, Downtown, Las Vegas, NV 89104", "900 S Las Vegas Blvd, Ste 120, Downtown, Las Vegas, NV 89101", "220 E Charleston Blvd, Downtown, Las Vegas, NV 89104"]


flags = ''

for user_coodin in origins_lst:
	flags += '&markers=color:red%7Clabel:U'
	flags += '%7C' + user_coodin

for i in range(0,10):
	coordin = coordinates[i]
	flags += "&markers=color:blue%7C"
	flags += "label:"
	flags += str(i)
	flags += '%7C' + coordin
	# print 'coordin:', coordin

# for coordin in coordinates:
# 	flags += "&markers=color:blue%7Clabel:R"
# 	flags += '%7C' + coordin
# 	print 'coordin:', coordin

data = "WIDTH = 800"
data += "\nHEIGHT = 500"
data +=  "\nLATITUDE = " + str(x)
data +=  "\nLONGITUDE = " + str(y)
data +=  "\nZOOM = 14"
data +=  "\nMAPTYPE = 'roadmap'"
data +=  "\nFLAGS = '" + flags + "'"

fo = open("goompy/key.py", "wb")
fo.write(data);
fo.close()

execfile('example.py')

























