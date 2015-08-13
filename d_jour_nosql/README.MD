#Djour Service


![Mou icon](https://bytebucket.org/mobiledi/djour-ios/raw/03d9bdfae3af5d93dfffa7b331a73c8609488863/DJour/images/djour_logo.png?token=479a42010c715e27c3a858f6ef5e6b8caaa54931)


***
`Note:` 
##### `Please Change directory to an empty folder before cloning a repo`
***
###Cloning the repository

####In your git bash type: 


```
#!shell command

git clone https://`USER-ID`@bitbucket.org/mobiledi/djour-api.git
```


e.g. : 


```
#!shell command

https://praks_mobile-di@bitbucket.org/mobiledi/djour-api.git
```



###Cloning only "develop" branch
####In your git bash type: 


```
#!shell command

git clone -b develop --single-branch https://`USER-ID`@bitbucket.org/mobiledi/djour-api.git
```


e.g. : 


```
#!shell command

git clone  -b develop --single-branch https://praks_mobile-di@bitbucket.org/mobiledi/djour-api.git
```



###Loading Project

* Open Eclipse File->Import->Existing Projects into Workspace

###Creating a new branch

Please confirm your current branch before creating a new branch using:	
```



```
#!shell command


git checkout <NEW_BRANCH_NAME>
```



e.g. 
#### Create a new branch named ui_modifications


```
#!shell command

'git branch new_feature'
```



##### Switch to the new branch

```
#!shell command

`git checkout new_feature`
```


***
#### Please confirm your current branch after checking out a new branch using:	

```
#!shell command

`git branch`
```