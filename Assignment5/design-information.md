This is a UML class diagram design for GroceryListManager. 
1. User can create grocery list from the main interface
2. Grocery list class has listName, itemInList, listID (preserved) attributes, and several methods. 
	When one item is added into the list, the itemInList is updated.
	The addItem method is pointing to two different classes: searchItem and hierarchical type search.
	The clearAllitem() and checkAllItem() methods should be able to update the checkOrNot boolean attributes in all items in the list.
	OrderList() method is recalled when the List is presented.
3. Item class is associated with grocery list (it is different from the items in the database). 
	All the items in the list has a name, quantity, (units are defined by database), check or not. 
	Several methods for this class.
	Once there is any modification to the item, the grocery list should be automatically updated (method updateGroceryList() recalled).
4. Both searchItem and hierarchical type search communicate with the item database.
	The specify search should be able to update the database as well when a new item is not found and defined by User.
5. Hierarchical list interface must be able to pull out the information of all items in the type selected from database.
6. When the item has be selected (defined) after searching, the item is listed in the groceryList and updated in item class.
7. The User interface must be intuitive and responsive.


