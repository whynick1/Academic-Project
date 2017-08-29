class Distance:
   "Contains resturant's address, distance"

   def __init__(self, Id, name, coordinate, distance, star):
      self.Id = Id
      self.name = name
      self.distance = distance
      self.coordinate = coordinate
      self.star = star
      self.nlp_score = 3.0

   def __repr__(self):
      return repr((self.name, self.Id, self.coordinate, self.distance))

   def displayResturant(self):
      print "Resturant ID: ", self.Id, ", Name: ", self.name, ", Distance: ", self.distance