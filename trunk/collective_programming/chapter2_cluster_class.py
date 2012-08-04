class Bicluster:
    def __init__(self, vector, left=None, right=None,distance=0.0,id=None):
        self.left = left
        self.right = right
        self.vector = vector
        self.id = id
        self.distance = distance
    