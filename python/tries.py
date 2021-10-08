
class Node:
    def __init__(self, identifier = 1, data = {}):
        """Creates a try node"""
        self.identifier = identifier
        self.data = data
        self.is_word = False
        self.children = {}


class Trie:
    def __init__(self):
        """Creates a trie with empty root"""
        self.root = Node()

    def add(self,word, data):
        if (word == None or len(word) < 1):
            return
        self.add_helper(word, data)

    def add_helper(self,word, data):
        curr = self.root
        for i in word:
            if i not in curr.children:
                curr.children[i] = Node(i)
            curr = curr.children[i]
        curr.is_word = True
        curr.data = data

    def add_dict(self,words_dict, key_assigned=None):
        """Adds list of dictionaries with key value stored in dict"""
        for i in words_dict:
            if key_assigned:
                self.add(words_dict[i][key_assigned],words_dict[i])
            else:
                self.add(i,words_dict[i])



    def keys_with_prefix(self, prefix):
        """
        """
        if prefix is None or prefix == "":
            return

        words_list = []
        curr = self.root
        for i in prefix:
            """traverse curr to end of prefix available in root"""
            if i not in curr.children:
                return words_list
            curr = curr.children[i]

        return self.clean_up(prefix,words_list,curr)

    def get_data(self, word):
        curr = self.root
        for i in word:
            if i not in curr.children:
                return {}
            curr = curr.children[i]
        return curr.data


    def clean_up(self, prefix, word_list, curr_node):
        """Checks if curr_node is a word, if so, appends to list, otherwise recursively
        looks in the rest of the trie"""
        if curr_node.is_word:
            word_list.append(prefix)
        for i in curr_node.children:
            child_node = curr_node.children[i]
            self.clean_up(prefix+child_node.identifier, word_list, child_node)
        return word_list
