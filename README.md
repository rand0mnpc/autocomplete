I generated application boilerplate for this project using: https://start.spring.io/
and built it up from there. My general approach to this project was to create an
application that was simple and easy to understand, but demonstrates my ability to
think ahead to how the application might change.

For example, there are doubtlessly more efficient ways to autocomplete strings,
different ways in which the customer might want the autocompletion to function, etc.
For that reason, I followed an orchestration pattern where the actual method for
autocompletion was abstracted out to an interface. In a team setting, some people 
could be working on new autocompletion strategies while others modified the
orchestration in parallel reducing delivery time of new features.

In line with that pattern I also abstracted the loading of the source file. Given
more time, I would have loved to load that file from S3 with and SNS/SQS setup that
allows the application to dynamically load different source test without needing
to restart. However, I left room for that improvement by abstracting out the source
loading to another interface.

I wrote unit tests for the important logical components of this project because I find
it very difficult to develop without them. They are essential in any team setting and
help me think through how I want things organized and working. They are also great for
debugging :D

The integration tests are not as strictly necessary for my working process here, although 
they allowed me to test a lot faster than opening up my browser and typing in individual 
words.

Source file credit: https://github.com/dwyl/english-words/blob/master/words.txt