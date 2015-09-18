/*this is the example code part 1 for you to use as reference! Feel free to make programs based off of these to test and learn how they work. The syntax is very very similar to java. This is one way to comment */

//this is another way to comment. this one is for one liners :D


/*these are your "import" files, as it would be named in Java. Allegro is a library, this is how you get access to it's functions, as well as the
  c/c++ standard library!

NOTE: the " < > " are for libraries that are system wide... if you have multiple files locally within the same folder as your source code, you'd use " "  (quotation marks)

example:

#include "pokemon.h" if I had a pokemon header file!  */

#include <allegro5/allegro.h>
#include <allegro5/allegro_font.h>
#include <allegro5/allegro_ttf.h>
#include <allegro5/allegro_image.h>
#include <allegro5/allegro_primitives.h>
#include <iostream>
#include <cstdlib>
#include <fstream>
#include <string>

using namespace std; //this is not really important to understand, it simply makes a class full name a nickname. Instead of std::cout << "hello"; it's cout << "hello"


//here's the main function obviously. This is like your main class in Java, and the main method of that class. You only have one of these for the whole project!

int main()
{
    string name; //this is a string obviously.. techincally it is a class/object... but you can use it just like a string... cout << name << endl; for example
    int display_width = 1280; // look for when this is used!
    int display_height = 800;
    ALLEGRO_DISPLAY *display = NULL;  //this is a pointer that allows the display to work. I will get to pointers some other time lol
    ALLEGRO_EVENT_QUEUE *event_queue = NULL;

      /*this will point to an image to draw unto the display*/
    ALLEGRO_BITMAP *image = NULL;

    //initializes allegro library. Or else none of the functions will work
    if(!al_init()) {
        cout<<"failed to initialize allegro!\n"<< endl; //this is how you print things to the terminal!
        //return -1;
    }

    al_init_ttf_addon(); //allows truetype font, which is an advanced font type
    al_init_image_addon(); //initializes and allows for image types such as PNG, JPEG, etc...



    int x;
    cout << "Give me a number peasant!" << endl;
    cin >> x; //this is one way to get formatted input.
    cout << "Your number was " << x << ", right?" << endl; //this is how you put values in print statements
    al_rest(3.0); //halts the program for 3 seconds

    event_queue = al_create_event_queue(); //this allows you to get events, such as keyboard presses, display closing, timer going off

    display = al_create_display(display_width, display_height); //this is the function to create a window like JFRAME depending on the parameters. ONLY CREATE ONE! Close with al_destroy_display(display)
    al_set_window_title(display, "PamlicoWist"); //sets the title of the program on the title bar

    al_register_event_source(event_queue, al_get_display_event_source(display)); //registers the display so it can recognize if something happens to the display
    al_rest(2.0); //halts the program for 2 seconds, then the display should turn yellow!!!

    al_clear_to_color(al_map_rgb(255, 255, 1)); //this colors the display a range of values 0-255 for red, green, blue. Display will turn yellow!!! Red+green = yellow
    al_flip_display(); //this "updates" whatever you changed for the display so you can see the output (which is the new color yellow). REMEMBER THIS ONE or you may be scratching head a lot

    al_rest(3.0);

//if you uncomment these three lines... you can play around with images
    //image = al_load_bitmap("*filename*");  //you must give the location to the image FROM where the source file is at, and don't forget the .png/.jpg at the end!

    //al_draw_bitmap(image, x, y, 0) //draws the image you loaded.. x is the distance width wise from the upper top left pixel... y is the height form the upper leftmost pixel.. use 0 for both usually!

    //al_flip_display(); //flips the display

    //al_destroy_bitmap(image);  //destroys the image from memory

    al_destroy_display(display); //destroy and close the display... only destroy a pointer you've made

    return 0;

    }

    /*to compile the program in Ubuntu... try this in the terminal:

    g++ zach_example.cpp -o myprogram  `pkg-config --libs allegro-5.0 allegro_font-5.0 allegro_ttf-5.0 allegro_image-5.0 allegro_primitives-5.0` -std=c++0x

    when that finishes compiling... in the same directory... type in ./myprogram

    that should work for you. myprogram is the name of the desired program name. The best method is to get it to work with codeblocks, but we'll cross that bridge when we get to it.
    hope that it isn't too complex, it's mostly functions and arguments to play around with.
    
    THE DOCUMENTATION/INTERFACE FOR EVERYTHING IS HERE: https://www.allegro.cc/manual/5/index.html
    */
