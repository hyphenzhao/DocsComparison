# DocsComparison
## Pre-requirements:
1. Download and install [Java Runtime Environment (JRE)](https://www.java.com/en/download/)
2. Download the App [DocComparison.jar](https://drive.google.com/open?id=1fy8ri37NjXHdgrG4Ch635OpEt-gJkQ_j)

## User Instructions:
1. ~~Please be aware that this app could only process **plain text file**. You have to respectively convert or copy/paste your two documents into **.txt** format. ~~
Now this App could support txt/doc/docx format. Please do not change extension name. If you want to use plain text, please use convert function in Microsoft Word insteadly. Here is a tutorial if you found problems of converting the format: [How to convert Word Document files into plain-text files](https://wmtang.org/2008/01/11/how-to-convert-word-document-files-into-plain-text-files/)

2. Double click the App, it should be run if you have properly installed JRE. If the **.jar** file does not turn into executable file, please try to restart computer after **JRE installation.**
3. Click on the **“Load paper 1”** button to browse and choose your first document. Click on the **“Load paper 2”** button to browse and choose your second document. It may take some time to load the text if the paper is very large.
4. You could specify the **“similarity check threshold”** by changing the number in the bottom. This number should between 0 to 90. 0 means only the exactly duplicated sentences will be highlighted while 90 means basically all sentences will be highlighted. I would recommend value less than 30 according to my experience during the debug stage. The highlight colour will be orange and green where **orange means “These sentences are exactly same in two documents” and green means “These sentences have either been changed or paraphrased in the other document”.**
5. Click **“Compare Now!”** button to start compare. It may takes some time to process the comparison. Please be patient if the document is very large. If it does not response in more than 5 mins, please terminate the app and contact me. I’ll double check the code to see if there is any undetected bugs there.
+ The example result picture:

![example pic](https://github.com/hyphenzhao/DocsComparison/blob/master/demopic2.png)

## Advantages:
1. Simple.
2. Completely offline and secure. 

## Major Deficiencies:
1. All matches will be highlighted in the same colour. It is hard to see where sentence is corresponding to the same sentence in the other document.
2. Similarity check algorithm is not as smart as we expected. 
+ Please note these problems could be resolved but take time. Hence, I could further improve it based on your specification.
