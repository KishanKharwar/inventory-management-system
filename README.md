Inventory-Management-System
===========================


Content
=======
1. [Installation/Quick Guide](#quick-guide)
1. [How to use/Usage](#usage)


Installation
============

Quick Guide
-----------
1. Download, install and set the classpath from here [Java Development Kit] (https://www.guru99.com/install-java.html)
1. Download eclipse as an IDE to run the project from here [Eclipse ] (http://www.eclipse.org/downloads/)

How to use
==========

Usage
-----

### Running JAVA programs

The project will run from com.ims.main.IMSMain class : 
1. right click on com.ims.main.IMSMain
1. Run As -> Java Application

Run In CLI : 
javaw.exe -classpath ./bin com.ims.main.IMSMain



Design Patterns Used
============
1. We have followed SOLID Design Principle in creating the project.
1. In class ItemsDao, we have followed Cache Design Pattern and DAO Design Pattern.
1. In class CardServiceImpl we have followed Adapter Design Pattern.
1. In all the model classes, we have followed Value Object Design Pattern.


Information about Files
----------------------------
- items.csv : item database
- cards.csv : cards database
- config.txt : add configuration for maximum allowed items per category.
- input.csv : add input details in the given format Items,Quantity,CardNumber
- output.txt : output file
- class diagram : [image](./ims-class-diagram.png)









