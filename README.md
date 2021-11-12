
Oliver McLane 
November 11, 2021
Code Documentation
Nate Williams 
                                    External Design and Documentation: Tone
In my Lab 2 Tone, I have created a program that can read sheet music via the “String[] args” parameter. The program reads the args as a file and will use if statement to determine if the notes are valid. Next the strings are passed to the members, where their thread begins its journey, these members will be thrown into a list that is synchronized. The Conductor will go along and check each member within the list and will ask them to play their note. At which point the member will call play(), which will convert their note string and note length passed by the conductor to a BellNote. After the song completes, the method notesDone() is called and closes all threads.
In the process of completing this I faced several challenges, most of them were pertain towards the actual design. In particular, when designing the MemberList class, I struggled with the ArrayList not find the member, but when I switched to a map it was simple to find the member. The next struggled I was stuck on forever was that my conductor would stop after half the song was played. This was due to my get function in my Conductor class, was removing the object from the list, which meant my for loops ended early. To fix this I simply set a counter before the loop and then made it remove everything form the list until the entire song was played. I didn’t have to many struggles with the thread playing, and the timings were pretty good.
