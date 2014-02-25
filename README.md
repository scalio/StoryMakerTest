StoryMakerTest
==============

A test project for [StoryMaker](http://storymaker.cc/) that utilizes Espresso

###Credits

StoryMakerTest was developed by [Scal.io](http://scal.io) and [Small World News](http://smallworldnews.tv/) as part of the [StoryMaker](http://storymaker.cc/) project with the generous support of [Open Technology Fund](https://www.opentechfund.org/).

[StoryMaker - Make your story great](http://storymaker.cc/)

### Setup

**Using Eclipse:**

1. Import the StoryMakerTest project into your StoryMaker Eclipse workspace.

2. Add the StoryMaker project to the Java Build Path of the StoryMakerTest project.

        StoryMaker -> Project Properties -> Java Build Path -> Projects(Tab) -> Add -> Select StoryMaker
        
3. Add a JUnit Test Run Configuration.

	    Run -> Run Configurations -> (Left Pane) StoryMakerTest -> Test (Tab)
	    
   + In the Test tab:
       + Make sure "Run all tests in the selected project" is set to "StoryMakerTest"
       + Make sure "Instrumentation Runner" is set to "com.google.android.apps.common.testing.testrunner.GoogleInstrumentationTestRunner"




### Authors

- [Josh Steiner](https://github.com/vitriolix/)
- [Micah Lucas](https://github.com/micahjlucas/)
