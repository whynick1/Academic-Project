class Resturant:
   'Contains resturant name, address, coordinate and star on Yelp'

   def __init__(self, Id, name, address, latitude, longtitude, star):
		self.Id = Id
		self.name = name
		self.address = address
		self.star = float(star)
		self.latitude = float(latitude)
		self.longtitude = float(longtitude)

   def displayResturant(self):
      print "Name : ", self.name, ", ID : ", self.Id, ", Address: ", self.address,  ", Coordinate: ", self.latitude, self.longtitude, ", Star: ", self.star