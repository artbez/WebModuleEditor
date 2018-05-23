# WebModuleEditor

WebModuleEditor is a web diagram editor for full pipeline working with existing convolutional neural network architectures.
It means you can choose datasets (like CIFAR-10 or ImageNet) and architectures(like VGG16 or AlexNet) you need, 
apply some actions (like evaluate or train) to them and show result.

### Backend

Backend has a micro service architecture which is based on Spring Cloud framework.
Today config, discovery, gateway and net-services are available.

Config service connects with git repository in application-config folder 
and distributes configuration (like hostname and port) files to other services.

Discovery service registers others services and allows you to refer to them by name.

Gateway service creates a unified entry point for all connections to backend environment.

Net-service is a service for working with neural networks.

Provided API (for today):

1. init diagram (from client) and get context id
2. evaluate model on existing dataset and get accuracy of trained net

You can also find mnist-service. Which is a simple example of constructed by hands architecture of CNN.
A simple test in test section section describes a script for requesting an image class on the image itself.

### Common

There is a subproject which is called 'Common'. There you can find classes which are translated to jvm and js both.
So you can use them on server and client sides.

### Frontend

Frontend is written using KotlinJs and React framework. It can be divided into 2 parts: view and logic.

View contains scene, palette and configurer. Scene is a place where you can construct your diagrams. 
Palette is a container for node types, where you can pick elements and drag and drop it to scene.
A configurator is a window for modifying the properties of nodes.  

![view](https://github.com/artbez/WebModuleEditor/blob/master/ex.png "Current view")

Logic include the way of executing diagram and defines the rules by which you can connect 
the elements of the diagram to each other.

Also you can find a folder 'wrappers' where I write some interfaces in order 
to ensure interaction with js and ts libraries.


## To run 

1. git clone hex.pngttps://github.com/artbez/WebModuleEditor.git

2. git submodule init

3. git submodule update

4. open IntelliJ and refresh gradle project

5. bootrun config service, wait 3-5 seconds

6. bootrun discovery service, wait 3-5 seconds

7. bootrun gateway service, wait 3-5 seconds

8. bootrun net-service service, wait 3-5 seconds

9. choose task run in frontend subproject

 


