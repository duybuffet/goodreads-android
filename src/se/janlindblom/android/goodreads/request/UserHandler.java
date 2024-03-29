package se.janlindblom.android.goodreads.request;

/**
 * $Id$
 * 
 * Copyright (c) 2009, Jan Lindblom
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the project nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import se.janlindblom.android.goodreads.Update;
import se.janlindblom.android.goodreads.meta.ReviewUpdate;
import se.janlindblom.android.goodreads.meta.Shelf;

/**
 * 
 * @author Jan Lindblom (lindblom.jan@gmail.com)
 * @version $Rev$
 *
 */
public class UserHandler extends DefaultHandler2 {
	
	private boolean inGoodreadsResponse = false;
    
    private boolean inRequest = false;
    /* Request fields */
    private boolean inAuthentication = false;
    private boolean inKey = false;
    private boolean inMethod = false;
    
    private boolean inUser = false;
    /* User fields */
    private boolean inName = false;
    private boolean inUserName = false;
    private boolean inUserId = false;
    private boolean inLink = false;
    private boolean inUserImageUrl = false;
    private boolean inUserImageSmallUrl = false;
    private boolean inUpdatesRssUrl = false;
    private boolean inReviewsRssUrl = false;
    private boolean inFriendsCount = false;
    private boolean inReviewsCount = false;
    private boolean inUserShelves = false;
    private boolean inUserShelf = false;
    /* User shelf fields */
    private boolean inBookCount = false;
    private boolean inDescription = false;
    private boolean inExclusiveFlag = false;
    private boolean inId = false;
    private boolean inShelfName = false;
    
    private boolean inUpdates = false;
    private boolean inUpdate = false;
    /* Update fields */
    private boolean inTitle = false;
    private boolean inUpdateLink = false;
    private boolean inUpdateDescription = false;
    private boolean inUpdateActionText = false;
    private boolean inUpdateImageUrl = false;
    private boolean inUpdateUpdatedAt = false;
    private boolean inUpdateActor = false;
    private boolean inUpdateCommentBody = false;
    /* Update Actor fields */
    private boolean inUpdateActorId = false;
    private boolean inUpdateActorName = false;
    private boolean inUpdateActorImageUrl = false;
    private boolean inUpdateActorLink = false;
    
    private Shelf currentShelf = null;
    private Update currentUpdate = null;

    private ParsedUserDataSet puds;
	
	private String currentString;
    
	public ParsedUserDataSet getParsedData() { 
		return this.puds;
	}
    
    @Override 
    public void startDocument() throws SAXException {
    	System.err.println("UserHandler::startDocument()");
    	this.puds = new ParsedUserDataSet();
    }
    
    @Override 
    public void endDocument() throws SAXException {
    	System.err.println("UserHandler::endDocument()");
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
    	System.err.println("UserHandler::startElement("+localName+")");
    	currentString = "";
    	if (localName.equals("GoodreadsResponse")) {
    		this.inGoodreadsResponse = true;
    	} else if (this.inGoodreadsResponse && localName.equals("Request")) {
    		this.inRequest = true;
   		} else if (this.inRequest && localName.equals("authentication")) {
   			this.inAuthentication = true;
   		} else if (this.inRequest && localName.equals("key")) {
   			this.inKey = true;
   		} else if (this.inRequest && localName.equals("method")) {
   			this.inMethod = true;
   		} else if (localName.equals("user")) {
   			this.inUser = true;
   		} else if (this.inUser && localName.equals("id") && !this.inUserShelves) {
   			this.inUserId = true;
   		} else if (this.inUser && localName.equals("name") && !this.inUserShelves) {
        	 this.inName = true;
         } else if (this.inUser && localName.equals("user-name")) {
        	 this.inUserName = true;
         } else if (this.inUser && localName.equals("friends-count")) {
        	 this.inFriendsCount = true;
         } else if (this.inUser && localName.equals("reviews-count")) {
        	 this.inReviewsCount = true;
         } else if (this.inUser && !this.inUpdate && localName.equals("link")) {
        	 this.inLink = true;
         } else if (this.inUser && localName.equals("user_shelves")) {
        	 this.inUserShelves = true;
         } else if (this.inUser && this.inUserShelves && localName.equals("user_shelf")) {
        	 this.inUserShelf = true;
        	 currentShelf = new Shelf();
         } else if (this.inUser && this.inUserShelves && this.inUserShelf && localName.equals("book_count")) {
        	 this.inBookCount = true;
         } else if (this.inUser && this.inUserShelves && this.inUserShelf && localName.equals("description")) {
        	 this.inDescription = true;
         } else if (this.inUser && this.inUserShelves && this.inUserShelf && localName.equals("exclusive_flag")) {
        	 this.inExclusiveFlag = true;
         } else if (this.inUser && this.inUserShelves && this.inUserShelf && localName.equals("id")) {
        	 this.inId = true;
         } else if (this.inUser && this.inUserShelves && this.inUserShelf && localName.equals("name")) {
        	 this.inShelfName = true;
         } else if (this.inUser && localName.equals("updates")) {
        	 this.inUpdates = true;
         } else if (this.inUser && this.inUpdates && localName.equals("update")) {
        	 this.inUpdate = true;
        	 if (atts.getValue("type").equals("review")) {
        		 currentUpdate = new ReviewUpdate();
        	 }
         } else if (this.inUser && this.inUpdates && this.inUpdate && localName.equals("title")) {
        	 this.inTitle = true;
         } else if (this.inUser && this.inUpdates && this.inUpdate && localName.equals("link")) {
        	 this.inUpdateLink = true;
         } else if (this.inUser && this.inUpdates && this.inUpdate && localName.equals("description")) {
        	 this.inUpdateDescription = true;
         } 
    }
    
    @Override 
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
    	System.err.println("UserHandler::endElement("+localName+")");
    	if (localName.equals("GoodreadsResponse")) { 
            this.inGoodreadsResponse = false;
       } else if (this.inGoodreadsResponse && localName.equals("Request")) {
    	   this.inRequest = false;
       } else if (this.inRequest && localName.equals("authentication")) {
    	   this.inAuthentication = false;
       } else if (this.inRequest && localName.equals("key")) {
    	   this.inKey = false;
       } else if (this.inRequest && localName.equals("method")) {
    	   this.inMethod = false;
       } else if (localName.equals("user")) {
    	   this.inUser = false;
       } else if (this.inUser && localName.equals("id") && !this.inUserShelves) {
  			this.inUserId = false;
       } else if (this.inUser && localName.equals("name") && !this.inUserShelves) {
    	   this.inName = false;
       } else if (this.inUser && localName.equals("user-name")) {
    	   this.inUserName = false;
       } else if (this.inUser && localName.equals("friends-count")) {
    	   this.inFriendsCount = false;
       } else if (this.inUser && localName.equals("reviews-count")) {
    	   this.inReviewsCount = false;
       } else if (this.inUser && !this.inUpdate && localName.equals("link")) {
      	 this.inLink = false;
       } else if (this.inUser && localName.equals("user_shelves")) {
    	   this.inUserShelves = false;
       } else if (this.inUser && this.inUserShelves && localName.equals("user_shelf")) {
    	   puds.addShelf(currentShelf);
    	   this.inUserShelf = false;
       } else if (this.inUser && this.inUserShelves && this.inUserShelf && localName.equals("book_count")) {
    	   this.inBookCount = false;
       } else if (this.inUser && this.inUserShelves && this.inUserShelf && localName.equals("description")) {
    	   this.inDescription = false;
       } else if (this.inUser && this.inUserShelves && this.inUserShelf && localName.equals("exclusive_flag")) {
    	   this.inExclusiveFlag = false;
       } else if (this.inUser && this.inUserShelves && this.inUserShelf && localName.equals("id")) {
    	   this.inId = false;
       } else if (this.inUser && this.inUserShelves && this.inUserShelf && localName.equals("name")) {
    	   this.inShelfName = false;
       } else if (this.inUser && localName.equals("updates")) {
      	 this.inUpdates = false;
       } else if (this.inUser && this.inUpdates && localName.equals("update")) {
    	   if (puds != null)
    		   puds.addUpdate(currentUpdate);
    	   this.inUpdate = false;
       } else if (this.inUser && this.inUpdates && this.inUpdate && localName.equals("title")) {
    	   this.inTitle = false;
       } else if (this.inUser && this.inUpdates && this.inUpdate && localName.equals("link")) {
    	   this.inUpdateLink = false;
       } else if (this.inUser && this.inUpdates && this.inUpdate && localName.equals("description")) {
    	   this.inUpdateDescription = false;
       }
    }
    
    @Override 
    public void characters(char ch[], int start, int length) {
    	if (this.inAuthentication) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		puds.setExtractedAuthentication(currentString);
    	} else if (this.inKey) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		puds.setExtractedKey(currentString);
    	} else if (this.inMethod) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		puds.setExtractedMethod(currentString);
    	} else if (this.inName) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		puds.setExtractedName(currentString);
    	} else if (this.inUserId) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		int userid = Integer.parseInt(currentString);
   			puds.setExtractedUserId(userid);
    	} else if (this.inUserName) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		puds.setExtractedUserName(currentString);
    	} else if (this.inLink) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		puds.setExtractedLink(currentString);
    	} else if (this.inFriendsCount) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		puds.setExtractedFriendsCount(Integer.parseInt(currentString));
    	} else if (this.inReviewsCount) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		puds.setExtractedReviews(Integer.parseInt(currentString));
    	} else if (this.inBookCount) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		currentShelf.setCount(Integer.parseInt(currentString));
    	} else if (this.inDescription) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		currentShelf.setDescription(new String(ch, start, length));
    	} else if (this.inExclusiveFlag) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		boolean exclusive = false;
    		if (currentString.equals("true"))
    			exclusive = true;
    		currentShelf.setExclusive(exclusive);
    	} else if (this.inId) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		currentShelf.setId(Integer.parseInt(currentString));
    	} else if (this.inShelfName) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		currentShelf.setName(currentString);
    	} else if (this.inUpdateDescription) {
    		String newString = new String(ch, start, length);
    		newString = newString.trim();
    		currentString = currentString.concat(newString + " ");
    		currentUpdate.setDescription(currentString);
    	} else if (this.inTitle) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		currentUpdate.setTitle(currentString);
    	} else if (this.inUpdateLink) {
    		currentString = currentString.concat(new String(ch, start, length));
    		currentString = currentString.trim();
    		currentUpdate.setLink(currentString);
    	}
    }
}
