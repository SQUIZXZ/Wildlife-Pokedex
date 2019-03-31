package com.example.wildlifeapplication.Search;

import com.example.wildlifeapplication.R;
import com.example.wildlifeapplication.Search.AnimalInformation.Animal;

import java.util.ArrayList;

public class SearchForAnimalService implements ISearchForAnimalService {
    @Override
    public ArrayList<Animal> getAllAnimals() {
        ArrayList<Animal> listOfAllAnimals = new ArrayList<>();
        listOfAllAnimals.add(new Animal("Common Kingfisher", "Alcedo atthis",
                21, 24, "The adults are " +
                "unmistakeable, with a greenish blue head and wings and a bright orange front. " +
                "They have long beaks and short legs. Females are distinguishable from males by " +
                "the red underside on the beak. Juveniles are very much like adults, but with " +
                "slightly duller, greener plumage. Kingfishers fly fast, low over water, with " +
                "the occasional hover", "green, blue, orange, red", "Small, " +
                "slow-flowing rivers with plenty of fish and sometimes by lakes. They build nests" +
                " in holes dug vertically into sandy banks.", "All year round",
                "Kingfishers eat fish and aquatic insects, often caught after " +
                        "diving vertically", R.mipmap.common_kingfisher));
        listOfAllAnimals.add(new Animal("European Blue Tit ",
                "Cyanistes caeruleus", 10, 12,
                "Adults have blue wings and a yellow front with a small black " +
                        "beard stripe. The tops of their heads are blue and this is brighter in" +
                        " the males. Blue tits are distinguishable from Great Tits by the blue" +
                        " cap on the tops of their heads and the lack of a thick black stripe" +
                        "down the front of the body. When flying, the Blue Tit also lacks white" +
                        " down the side of the tail feathers. Juveniles look similar to the " +
                        "adults, but have a duller blue cap and yellow cheeks rather than white",
                "black, blue, white, yellow", "Woodland, parks and gardens.",
                "All year round", "Insects, seeds and nuts",
                R.mipmap.blue_tit));
        listOfAllAnimals.add(new Animal("Southern Hawker", "Aeshna cyanea", R.mipmap.southern_hawker));
        listOfAllAnimals.add(new Animal("Mute Swan", "Cygnus olor",
                140, 160, 200, 240,
                "Adults are all white, with males differing by their larger size" +
                        " and larger billknob. Juveniles are a greybrown colour with a grey " +
                        "rather than orange beak.", "grey, brown",
                "Freshwater lakes and in coastal areas", "All year round",
                "Plants, insects and snails", R.mipmap.mute_swan));
        listOfAllAnimals.add(new Animal("Lesser Spotted Woodpecker",
                "Dendrocopos minor", 14, 17,
                24, 29, "Adults have black wings " +
                "striped with white and a pale belly. Males also have a red cap on their heads, " +
                "whereas in the females this is black. This bird has a deep undulating flight",
                "white, black, red", "Parks and old orchards and peck" +
                " holes into dead wood to make their nests", "All year round",
                "Insects", R.mipmap.lesser_spotted_woodpecker));
        listOfAllAnimals.add(new Animal("Cormorant", "Phalacrocorax carbo",
                77, 97, 121, 149,
                "Adults are all black with white cheeks. When breeding, adults " +
                        "appear glossier and have a small tuft of feathers at the back of the " +
                        "head. A white patch also appears at the top of the legs. Juveniles are " +
                        "distinguishable by their much lighter and often white chests and " +
                        "bellies. When in flight, cormorants often look like geese though with " +
                        "slower, less frequent wingbeats and a kinked neck",
                "black, white", "Generally coastal, but some " +
                "populations winter inland. They can build nests and breed in a variety of " +
                "places including cliff-ledges, in trees or on the ground", "All " +
                "year round","Cormorants are talented fishers, half-leaping into " +
                "dives without first looking", R.mipmap.cormorant));
        listOfAllAnimals.add(new Animal("European Green Woodpecker",
                "Picus viridis", 30, 36,
                45, 51, "Adults have a yellowy-green " +
                "plumage with a bright red stripe over the top of the head. In males, there is a " +
                "red oval shape beneath the eye and in females this is black. Juveniles are " +
                "similar colours, but have a black spotted front and white spots on the wings. " +
                "They fly in an undulating, wavy pattern with a yellow-green tail base visible",
                "yellow, red, black, white, green", "Woodland and " +
                "parks and build their nests in holes pecked into dead wood",
                "All year round", "These birds eat ants, using their " +
                "powerful beaks to dig into the nests", R.mipmap.green_woodpecker));
        listOfAllAnimals.add(new Animal("Large Yellow Underwing",
                "Noctua pronuba", R.mipmap.large_yellow_underwing));
        listOfAllAnimals.add(new Animal("Long-tailed Tit",
                "Aegithalos caudatus", 20, 34,
                " Adults have dark backs with reddish-brown patches. The heads " +
                        "are white with black bands down each side above the eye. Juveniles are " +
                        "whiter with darker sides to their heads. Long-tailed Tits fly with a " +
                        "skipping movement and short undulations. They look very small, with a " +
                        "tail longer than its body and are able to hang upside-down on thin " +
                        "twigs. They are seen in small groups moving quickly between trees",
                "red, brown, white, black", "Woods with lots of undergrowth",
                "All year round", "These birds are mostly " +
                "insectivorous, but will eat seeds in autumn and winter when there are fewer " +
                "insects about", R.mipmap.long_tailed_tit));
        listOfAllAnimals.add(new Animal("Robin", "Erithacus rubecula",
                12, 14, "Adults have brown wings, " +
                "a white belly and a reddish-orange chest and face. Juveniles are brown with a " +
                "pale chest and spotted with white", "red, orange, white, brown, white",
                "Anywhere with some vegetation and some open space including parks " +
                        "and garden", "All year round", "Invertebrates" +
                ", fruit and seeds", R.mipmap.robin));
        listOfAllAnimals.add(new Animal("Common Moorhen",
                "Gallinula chloropus", 27, 31,
                "Adults are dark grey with pale green legs and a bright red beak " +
                        "with a yellow tip. Juveniles are a grey-brown with a whitish chin and " +
                        "throat", "grey, green, yellow, brown, white",
                " Any small water body with dense vegetation", "All year" +
                " round", "A variety of plants, seeds, invertebrates and " +
                "small fish", R.mipmap.common_moorhen));
        listOfAllAnimals.add(new Animal("European Reed Warbler",
                "Acrocephalus scirpaceus", 12, 14,
                "Adults have quite a plain, grey-brown colouration, with a " +
                        "slightly redder tail base. In summer, quite a large peak of feathers " +
                        "grows on the top of the head. These birds are quite bold and easy to see",
                "black, grey, white, brown", "Reed beds",
                "Summer", "Insectivorous in the spring and summer, " +
                "these birds eat berries in autumn to build up fat reserves for the migration " +
                "back to Africa", R.mipmap.reed_warbler));
        listOfAllAnimals.add(new Animal("Whitethroat", "Sylvia communis",
                13, 15, "Adults have brownish-grey " +
                "plumage and bright orangey-brown wing feathers with dark stripes. Males have " +
                "a greyer head with a larger peak on their heads. All birds have a white throat " +
                "which gives them their name. Whitethroats move with quite heavy, slow movements," +
                " darting in and out of cover with tail flicks", "brown, grey, " +
                "orange, brown", "Farmland and along the edges of woodland",
                "Summer", "Insects and berries", R.mipmap.whitethroat));
        listOfAllAnimals.add(new Animal("Great Spotted Woodpecker",
                "Dendrocopos major", 23, 26,
                38, 44, "Adults have black wings " +
                "with white striping and a red belly. Males have a red patch on the back of " +
                "the head, but this is black in the females. Juveniles look very similar to the " +
                "adults but with much less red on the belly and a red patch on the top of the " +
                "head. The Great Spotted Woodpecker flies with deep undulations, showing the " +
                "large white oval patches at the base of the wings",
                "white, red, black", "Woodland and parks and build " +
                "their nests in holes pecked in dead wood", "All year round",
                "Insects, seeds and nuts", R.mipmap.great_spotted_woodpecker));
        return listOfAllAnimals;
    }
}
