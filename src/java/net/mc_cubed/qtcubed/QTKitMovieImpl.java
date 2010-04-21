//
//  QTKitMovieImpl.java
//  QTCubed
//
//  Created by Chappell Charles on 10/02/19.
//  Copyright (c) 2010 MC Cubed, Inc. All rights reserved.
//
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 2 of the License, or
//  (at your option) any later version.
//  
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//  
//  You should have received a copy of the GNU General Public License along
//  with this program; if not, write to the Free Software Foundation, Inc.,
//  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
//  
//  
//  This is also dual licensed as proprietary software, and may be used in
//  commercial/proprietary software products by obtaining a license from:
//  MC Cubed, Inc
//  1-3-4 Kamikizaki, Urawa-ku
//  Saitama, Saitama, 330-0071
//  Japan
//
//  Email: info@mc-cubed.net
//  Website: http://www.mc-cubed.net/

package net.mc_cubed.qtcubed;

import java.net.URL;
import java.io.File;
import java.util.Properties;
import java.io.IOException;
import java.lang.SecurityManager;
import java.security.AllPermission;

/**
 * Placeholder (for now) for QTMovie bridge class
 * 
 * @author shadow
 */
class QTKitMovieImpl implements QTMovie {

	private long movieRef;
	
	public long getMovieRef() {
		return movieRef;
	}
			
	/**
	 * Designated Initializer for QTMovie
	 * @param movieRef
	 *   Takes the movieRef created by a native function and stores it
	 */
	protected QTKitMovieImpl(long movieRef) throws InstantiationException {
		if (movieRef == 0)
		{
			throw new java.lang.InstantiationException("Didn't successfully obtain a QTMovie reference");
		}
		this.movieRef = movieRef;
	}

	public QTKitMovieImpl() throws InstantiationException {
		this(_movie());
	}

	public QTKitMovieImpl(URL url) throws InstantiationException {
		this(_movieWithURL(url.toString()));
		SecurityManager security = System.getSecurityManager();
		if (security != null) {
			security.checkConnect(url.getHost(),url.getPort());
		}
	}
	
	public QTKitMovieImpl(File file) throws InstantiationException,IOException {
		this(_movieWithFile(file.getCanonicalPath()));
		SecurityManager security = System.getSecurityManager();
		if (security != null) {
			security.checkRead(file.getAbsolutePath());
		}
		
	}
	
	public QTKitMovieImpl(byte[] bytes) throws InstantiationException {
		this(_movieWithData(bytes));
	}
	
	public QTKitMovieImpl(Properties attributes) throws InstantiationException {
		this(_movieWithAttributes(attributes));
		SecurityManager security = System.getSecurityManager();
		if (security != null) {
			security.checkPermission(new AllPermission());
		}
	}
	
	public QTKitMovieImpl(String name) throws InstantiationException {
		this(_movieNamed(name));
	}
	
	/**
	 * Determines whether the contents of the specified pasteboard can be used to initialize a QTMovie object.
	 * @param			pasteboard
	 *	An NSPasteboard object.
	 * @result			true if a QTMovie object can be initialized from the specified pasteboard, false otherwise.
	 */
	//public static boolean canInitWithPasteboard(NSPasteboard *pasteboard);
	
	/**
	 * Determines whether the contents of the specified file can be used to initialize a QTMovie object.
	 * @param			fileName
	 *   An NSString object that specifies a full pathname to a file.
	 * @result			true if a QTMovie object can be initialized from the specified file, false otherwise.
	 */
	native public static boolean _canInitWithFile(String filename) throws InstantiationException;
	
	public boolean canInit(File file) throws InstantiationException,IOException {
		SecurityManager security = System.getSecurityManager();
		if (security != null) {
			try {
				security.checkRead(file.getAbsolutePath());
			} catch (SecurityException se) {
				return false;
			}
		}
		
		return _canInitWithFile(file.getCanonicalPath());
	}
	
	/*!
	 @method			canInitWithURL:
	 @abstract		Determines whether the contents of the specified URL can be used to initialize a QTMovie object.
	 @param			url
	 An NSURL object.
	 @result			YES if a QTMovie object can be initialized from the specified URL, NO otherwise.
	 */
	native public static boolean _canInitWithURL(String url);
	
	public boolean canInit(URL url) {
		SecurityManager security = System.getSecurityManager();
		if (security != null) {
			try {
				security.checkConnect(url.getHost(),url.getPort());
			} catch (SecurityException se) {
				return false;
			}
		}
		
		return _canInitWithURL(url.toString());
	}
	
	/**
	 * Determines whether the specified data reference can be used to initialize a QTMovie object.
	 * @param			dataReference
	 *   An byte array.
	 * @result			true if a QTMovie object can be initialized from the specified data reference, false otherwise.
	 */
//	native public static boolean _canInitWithDataReference(byte[] dataReference);
	
	/**
	 @method			movieFileTypes:
	 @abstract		Returns an array of file types that can be used to initialize a QTMovie object.
	 @param			types
	 A value of type QTMovieFileTypeOptions that indicates the kinds of file types that are to be returned.
	 Passing QTIncludeCommonTypes as the types parameter returns an array of all the common file types that QTKit can open in place on the current system.
	 This array includes the file types .mov and .mqv, and any files types that can be opened using a movie importer
	 that does not need to write data into a new file while performing the import.
	 This array excludes any file types for still images and any file types that require an aggressive movie importer
	 (for instance, the movie importer for text files).
	 @result			An NSArray object that contains NSString objects indicating supported file types.
	 */	
	native protected static String[] _movieFileTypes(int types);

	public String[] movieFileTypes(QTMovieFileTypeOptions[] types){
		int type = 0;
		for (QTMovieFileTypeOptions opt : types) {
			type |= opt.valueOf();
		}
		return _movieFileTypes(type);
	}

	
	/**
	 @method			movieUnfilteredFileTypes
	 @abstract		Returns an array of file types that can be used to initialize a QTMovie object.
	 @result			An NSArray object that contains NSString objects indicating supported file types.
	 */
	native public String[] movieUnfilteredFileTypes();
	
	/**
	 @method			movieUnfilteredPasteboardTypes
	 @abstract		Returns an array of pasteboard types that can be used to initialize a QTMovie object.
	 @result			An NSArray object that contains NSString objects indicating supported pasteboard types.
	 */
	native protected String[] _movieUnfilteredPasteboardTypes();
		
	/**
	 @method			movieTypesWithOptions:
	 @abstract		Returns an array of UTIs that QTKit can open.
	 @param			types
	 A value of type QTMovieFileTypeOptions that indicates the kinds of UTIs that are to be returned.
	 See the description of +movieFileTypes for more information.
	 @result			An NSArray object that contains NSString objects indicating supported file types.
	 */
	public String[] movieTypesWithOptions(QTMovieFileTypeOptions[] types) {
		int type = 0;
		for (QTMovieFileTypeOptions opt : types) {
			type |= opt.valueOf();
		}
		return _movieTypesWithOptions(type);		
	}
	
	native protected static String[] _movieTypesWithOptions(int types);
		
	/**
	 @method			movie
	 @abstract		Creates an empty QTMovie object.
	 @result			An empty QTMovie object. This movie contains no playable data.
	 */	
	native static protected long _movie();
	
	/**
	 @method			movieWithFile:error:
	 @abstract		Creates a QTMovie object initialized with the data in a specified file.
	 @param			fileName
	 An NSString object that specifies a full pathname to a file.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			An autoreleased QTMovie object.
	 */
	native static protected long _movieWithFile(String filename) throws InstantiationException;
		
	/**
	 @method			movieWithURL:error:
	 @abstract		Creates a QTMovie object initialized with the contents of the specified URL.
	 @param			url
	 An NSURL object.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			An autoreleased QTMovie object.
	 */
	native static protected long _movieWithURL(String url) throws InstantiationException;
	
	/**
	 @method			movieWithDataReference:error:
	 @abstract		Creates a QTMovie object initialized with data specified by a data reference.
	 @param			dataReference
	 A QTDataReference object that specifies data from which a QTMovie object can be initialized.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			An autoreleased QTMovie object.
	 */
//	+ (id)movieWithDataReference:(QTDataReference *)dataReference error:(NSError **)errorPtr;
	
	/**
	 @method			movieWithPasteboard:error:
	 @abstract		Creates a QTMovie object initialized with the contents of the specified pasteboard.
	 @param			pasteboard
	 An NSPasteboard object that contains data from which a QTMovie object can be initialized.
	 The contents of the pasteboard can be a QuickTime movie (of type Movie), a file path, or data of type QTMoviePasteboardType.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			An autoreleased QTMovie object.
	 */
//	+ (id)movieWithPasteboard:(NSPasteboard *)pasteboard error:(NSError **)errorPtr;
	
	/*!
	 @method			movieWithData:error:
	 @abstract		Creates a QTMovie object initialized with data specified by an NSData object.
	 @param			data
	 An NSData object that contains data from which a QTMovie object can be initialized.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			An autoreleased QTMovie object.
	 */
	native static protected long _movieWithData(byte[] bytes) throws InstantiationException;
	
	/**
	 @method			movieWithQuickTimeMovie:disposeWhenDone:error:
	 @abstract		Creates a QTMovie object initialized from an existing QuickTime movie.
	 @discussion		This method cannot be called by 64-bit applications.
	 @param			movie
	 A QuickTime movie (of type Movie).
	 @param			dispose
	 A BOOL value that indicates whether QTKit should call DisposeMovie on the specified QuickTime movie
	 when the QTMovie object is deallocated. Passing YES effectively transfers ownership of the Movie to QTKit.
	 Most applications will probably want to pass YES; passing NO means that the application wants to call DisposeMovie itself,
	 perhaps so that it can operate on a Movie after it has been disassociated from a QTMovie object.
	 Command-line tools that pass NO for the dispose parameter must make sure to release the active autorelease pool
	 before calling DisposeMovie on the specified QuickTime movie. Failure to do this may result in a crash.
	 Tools that need to call DisposeMovie before releasing the main autorelease pool can create
	 another autorelease pool associated with the movie.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			An autoreleased QTMovie object.
	 */
//		+ (id)movieWithQuickTimeMovie:(Movie)movie disposeWhenDone:(BOOL)dispose error:(NSError **)errorPtr;
	
	/**
	 @method			movieWithAttributes:error:
	 @abstract		Creates a QTMovie object initialized with a specified set of attributes.
	 @param			attributes
	 An NSDictionary object whose key-value pairs specify the attributes to use when initializing the movie.
	 There are three types of attributes that can be included in this dictionary: (1) attributes that specify the location
	 of the movie data (for instance, QTMovieFileNameAttribute); (2) attributes that specify how the movie is to be instantiated
	 (for instance, QTMovieOpenForPlaybackAttribute); (3) attributes that specify playback characteristics of the movie
	 or other properties of the QTMovie object (for instance, QTMovieVolumeAttribute).
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			An autoreleased QTMovie object.
	 */
	native static protected long _movieWithAttributes(Properties attributes) throws InstantiationException;
	
	/**
	 @method			movieNamed:error:
	 @abstract		Creates a QTMovie object initialized with the data from the movie having the specified name in the application’s bundle.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			A QTMovie object.
	 */
	native static protected long _movieNamed(String name) throws InstantiationException;
	
	/**
	 @method			initWithFile:error:
	 @abstract		Creates a QTMovie object initialized with the data in a specified file.
	 @param			fileName
	 An NSString object that specifies a full pathname to a file.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			A QTMovie object.
	 */
	native protected void _initWithFile(String filename) throws InstantiationException;
	
	public void init(File file) throws InstantiationException,IOException {
		_initWithFile(file.getCanonicalPath());
	}
	
	/**
	 @method			initWithURL:error:
	 @abstract		Creates a QTMovie object initialized with the contents of the specified URL.
	 @param			url
	 An NSURL object.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			A QTMovie object.
	 */
	native protected void _initWithURL(String url) throws InstantiationException;
	
	public void init(URL url) throws InstantiationException {
		_initWithURL(url.toString());
	}
	
	/**
	 @method			initWithDataReference:error:
	 @abstract		Creates a QTMovie object initialized with data specified by a data reference.
	 @param			dataReference
	 A QTDataReference object that specifies data from which a QTMovie object can be initialized.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			A QTMovie object.
	 */
//	- (id)initWithDataReference:(QTDataReference *)dataReference error:(NSError **)errorPtr;
	
	/**
	 @method			initWithPasteboard:error:
	 @abstract		Creates a QTMovie object initialized with the contents of the specified pasteboard.
	 @param			pasteboard
	 An NSPasteboard object that contains data from which a QTMovie object can be initialized.
	 The contents of the pasteboard can be a QuickTime movie (of type Movie), a file path, or data of type QTMoviePasteboardType.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			A QTMovie object.
	 */
//	- (id)initWithPasteboard:(NSPasteboard *)pasteboard error:(NSError **)errorPtr;
	
	/**
	 @method			initWithData:error:
	 @abstract		Creates a QTMovie object initialized with data specified by an NSData object.
	 @param			data
	 An NSData object that contains data from which a QTMovie object can be initialized.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			A QTMovie object.
	 */
	native protected void _initWithData(byte[] bytes) throws InstantiationException;
	
	public void init(byte[] bytes) throws InstantiationException {
		_initWithData(bytes);
	}
	
	/**
	 @method			initWithMovie:timeRange:error:
	 @abstract		Creates a QTMovie object initialized with some or all of the data from an existing QTMovie object.
	 @param			movie
	 A QTMovie object.
	 @param			range
	 A QTTimeRange structure that delimits the segment of data from the movie parameter to be used to initialize the receiver.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			A QTMovie object.
	 */
	native protected void _initWithMovie(QTMovie movie,QTTimeRange range);
	
	public void init(QTMovie movie, QTTimeRange range) {
		_initWithMovie(movie,range);
	}
	
	/**
	 @method			initWithQuickTimeMovie:disposeWhenDone:error:
	 @abstract		Creates a QTMovie object initialized from an existing QuickTime movie.
	 @discussion		This method cannot be called by 64-bit applications.
	 @param			movie
	 A QuickTime movie (of type Movie).
	 @param			dispose
	 A BOOL value that indicates whether QTKit should call DisposeMovie on the specified QuickTime movie
	 when the QTMovie object is deallocated. Passing YES effectively transfers ownership of the Movie to QTKit.
	 Most applications will probably want to pass YES; passing NO means that the application wants to call DisposeMovie itself,
	 perhaps so that it can operate on a Movie after it has been disassociated from a QTMovie object.
	 Command-line tools that pass NO for the dispose parameter must make sure to release the active autorelease pool
	 before calling DisposeMovie on the specified QuickTime movie. Failure to do this may result in a crash.
	 Tools that need to call DisposeMovie before releasing the main autorelease pool can create
	 another autorelease pool associated with the movie.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			A QTMovie object.
	 */
//		- (id)initWithQuickTimeMovie:(Movie)movie disposeWhenDone:(BOOL)dispose error:(NSError **)errorPtr;
	
	/**
	 @method			initWithAttributes:error:
	 @abstract		Creates a QTMovie object initialized with a specified set of attributes.
	 @param			attributes
	 An NSDictionary object whose key-value pairs specify the attributes to use when initializing the movie.
	 There are three types of attributes that can be included in this dictionary: (1) attributes that specify the location
	 of the movie data (for instance, QTMovieFileNameAttribute); (2) attributes that specify how the movie is to be instantiated
	 (for instance, QTMovieOpenForPlaybackAttribute); (3) attributes that specify playback characteristics of the movie
	 or other properties of the QTMovie object (for instance, QTMovieVolumeAttribute).
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			A QTMovie object.
	 */
//	- (id)initWithAttributes:(NSDictionary *)attributes error:(NSError **)errorPtr;
	
	/**
	 @method			movieWithTimeRange:error:
	 @abstract		Returns a QTMovie object initialized with the data in the specified time range of an existing QTMovie object.
	 @discussion		This method cannot be called when the movie has been initialized with QTMovieOpenForPlaybackAttribute set to YES.
	 @param			range
	 A QTTimeRange structure that indicates the segment in the movie to use to initialize a QTMovie object.
	 @param			errorPtr
	 A pointer to an NSError object; if a movie cannot be created, an NSError object is returned in this location.
	 @result			A QTMovie object.
	 */
//	- (id)movieWithTimeRange:(QTTimeRange)range error:(NSError **)errorPtr;
	
	
	/**
	 @method			initToWritableFile:error:
	 @abstract		Creates an empty, writable storage container at the location specified by the data reference and returns an editable QTMovie object associated with that container.
	 @discussion		Movie data can be added to the QTMovie object returned by this method (for example, using the addImage:forDuration:withAttributes: method).
	 This method cannot be called when the movie has been initialized with QTMovieOpenForPlaybackAttribute set to YES.
	 @param			filename
	 An NSString object that specifies a full pathname to a file.
	 @param			errorPtr
	 A pointer to an NSError object; if the storage container or the QTMovie object cannot be created, an NSError object is returned in this location.
	 @result			A QTMovie object. This object is editable.
	 */
//		- (id)initToWritableFile:(NSString *)filename error:(NSError **)errorPtr;
		
	/**
	 @method			initToWritableData:error:
	 @abstract		Creates an empty, writable storage container in memory and returns an editable QTMovie object associated with that container.
	 @discussion		Movie data can be added to the QTMovie object returned by this method (for example, using the addImage:forDuration:withAttributes: method).
	 This method cannot be called when the movie has been initialized with QTMovieOpenForPlaybackAttribute set to YES.
	 @param			data
	 An NSMutableData object.
	 @param			errorPtr
	 A pointer to an NSError object; if the storage container or the QTMovie object cannot be created, an NSError object is returned in this location.
	 @result			A QTMovie object. This object is editable.
	 */
//		- (id)initToWritableData:(NSMutableData *)data error:(NSError **)errorPtr;
	
	/**
	 @method			initToWritableDataReference:error:
	 @abstract		Creates an empty, writable storage container at the location specified by the data reference and returns an editable QTMovie object associated with that container.
	 @discussion		Movie data can be added to the QTMovie object returned by this method (for example, using the addImage:forDuration:withAttributes: method).
	 This method cannot be called when the movie has been initialized with QTMovieOpenForPlaybackAttribute set to YES.
	 @param			dataReference
	 A QTDataReference object.
	 @param			errorPtr
	 A pointer to an NSError object; if the storage container or the QTMovie object cannot be created, an NSError object is returned in this location.
	 @result			A QTMovie object. This object is editable.
	 */
//	- (id)initToWritableDataReference:(QTDataReference *)dataReference error:(NSError **)errorPtr;
	
	/**
	 @method			invalidate
	 @abstract		Invalidates a QTMovie object immediately.
	 @discussion		By the time this method has returned, the receiver will have detached itself from any resources it is using, 
	 disposing of these resources when appropriate.
	 Attempting to make any non-trivial use of the receiver after invalidating it will result in undefined behavior.
	 This method does not release the receiver, so under retain/release memory management
	 release must still be called on the receiver for it to be fully deallocated.
	 Because this method defeats sharing of QTMovie objects,
	 it should only be called when it is known that the object is no longer needed.
	 This method can be called when the movie has been initialized with QTMovieOpenForPlaybackAttribute set to YES.
	 */
	protected native void _invalidate();
		
	protected void finalize() throws Throwable
	{
		// Destroy the native QTMovie reference
		_invalidate();
		super.finalize();
	}
	
	@Override
	public String toString() {
		return getClass().getName() + "[movieRef=" + movieRef + "]";
	}
}
