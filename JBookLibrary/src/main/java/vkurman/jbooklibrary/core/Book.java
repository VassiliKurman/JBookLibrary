package vkurman.jbooklibrary.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import vkurman.jbooklibrary.enums.ItemStatus;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;

/**
 * Book class has all details necessary to hold data about books.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class Book{
	private long bookID;
	private int edition, pagination, recommendedAge;
	private BigDecimal price;
	private ItemStatus status;
	private String title;
	private String subtitle;
	private String language;
	private String description;
	private String condition;
	private String editor;
	private String publicationPlace;
	private String publisher;
	private String isbn;
	private String format; // Can be electronic or paper format
	private String location;
	private String translatedFrom;
	private String series;
	private String supplements;
	private String footNote;
	private Calendar dateOfEntry;
	private Calendar publicationDate;
	private List<String> authors;
	private List<String> subjectHeadings;
	private List<String> genres;
	private List<String> keywords;
	
	/**
	 * Default Constructor
	 */
	public Book(){
		this(
				new ArrayList<String>(),
				null,
				1,
				null,
				null,
				null,
				null,
				null,
				0,
				null,
				null);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param title
	 */
	public Book(String title){
		this(
				new ArrayList<String>(),
				title,
				1,
				null,
				null,
				null,
				null,
				null,
				0,
				null,
				null);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param title
	 * @param edition
	 * @param publicationDate
	 * @param publicationPlace
	 * @param publisher
	 */
	public Book(
			String title,
			int edition,
			Calendar publicationDate,
			String publicationPlace,
			String publisher) {
					this(
							new ArrayList<String>(),
							title,
							edition,
							publicationDate,
							publicationPlace,
							publisher,
							null,
							null,
							0,
							null,
							null);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param authors
	 * @param title
	 * @param edition
	 * @param publicationDate
	 * @param publicationPlace
	 * @param publisher
	 * @param language
	 * @param isbn
	 * @param pagination
	 * @param description
	 * @param format
	 */
	public Book(
			List<String> authors,
			String title,
			int edition,
			Calendar publicationDate,
			String publicationPlace,
			String publisher,
			String language,
			String isbn,
			int pagination,
			String description,
			String format) {
					this.title = title;
					this.edition = edition;
					this.publicationDate = publicationDate;
					this.publicationPlace = publicationPlace;
					this.publisher = publisher;
					this.language = language;
					this.isbn = isbn;
					this.pagination = pagination;
					this.description = description;
					this.format = format;
					this.price = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_EVEN);
					this.condition = "New";
					this.dateOfEntry = (Calendar) Calendar.getInstance(Locale.getDefault()).clone();
					this.status = ItemStatus.ONSHELF;
					
					this.authors = new ArrayList<String>();
					if(!authors.isEmpty()){
						this.authors.addAll(authors);
					}
					this.subjectHeadings = new ArrayList<String>();
					this.genres = new ArrayList<String>();
					this.keywords = new ArrayList<String>();
	}
	
	/**
	 * Getter for 'recommendedAge'
	 * 
	 * @return int
	 */
	public int getRecommendedAge() {
		return recommendedAge;
	}
	
	/**
	 * Setter for 'recommendedAge'
	 * 
	 * @param recommendedAge
	 */
	public void setRecommendedAge(int recommendedAge) {
		this.recommendedAge = recommendedAge;
	}
	
	/**
	 * Getter for book physical 'location' in the library
	 * 
	 * @return String
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Setter for book physical 'location' in the library
	 * 
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Getter for book 'subjectHeadings'
	 * 
	 * @return List
	 */
	public List<String> getSubjectHeadings() {
		return subjectHeadings;
	}
	
	/**
	 * This method is returning a String that contains all comma
	 * separated subjectHeadings from the list of subjectHeadings.
	 * 
	 * @return String
	 */
	public String getSubjectHeadingsAsString(){
		if(subjectHeadings.isEmpty() || subjectHeadings == null) return "";
		
		StringBuilder sb = new StringBuilder();
		Iterator<String> iter = subjectHeadings.iterator();
		while(iter.hasNext()){
			String s = iter.next();
			if(!s.equals("")){
				sb.append(s);
				if(iter.hasNext()){
					sb.append(", ");
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * This method is setting subjectHeadings from the list passed as
	 * a parameter. If existing list of subjectHeadings is not empty,
	 * than existing list is cleared first and than new subjectHeadings
	 * are added to the existing empty list.
	 * 
	 * @param subjectHeadings
	 */
	public void setSubjectHeadings(List<String> subjectHeadings) {
		if(this.subjectHeadings.isEmpty()){
			this.subjectHeadings.addAll(subjectHeadings);
		} else {
			this.subjectHeadings.clear();
			this.subjectHeadings.addAll(subjectHeadings);
		}
	}
	
	/**
	 * This methods is setting subjectHeadings to the list of
	 * subjectHeadings passed as a single line comma separated
	 * String object.
	 * 
	 * @param subjectHeadings
	 */
	public void setSubjectHeadingsFromString(String subjectHeadings) {
		if(subjectHeadings != null){
			List<String> list = Arrays.asList(subjectHeadings.split("\\s*,\\s*"));
			
			Iterator<String> iter = list.iterator();
			while(iter.hasNext()){
				String s = iter.next();
				if(!s.equals("")){
					this.subjectHeadings.add(s);
				}
			}
		}
	}
	
	/**
	 * This method adds specified subjectHeading to the list of subjectHeadings.
	 * 
	 * @param subjectHeading
	 * @return boolean
	 */
	public boolean addSubjectHeading(String subjectHeading) {
		if(subjectHeading == null) return false;
		
		return (!subjectHeadings.contains(subjectHeading)) ?
				subjectHeadings.add(subjectHeading) :
					false;
	}
	
	/**
	 * This method removes specified subjectHeading to the list of subjectHeadings.
	 * 
	 * @param subjectHeading
	 * @return boolean
	 */
	public boolean removeSubjectHeading(String subjectHeading) {
		if(subjectHeading == null) return false;
		
		return (subjectHeadings.contains(subjectHeading)) ?
				subjectHeadings.remove(subjectHeading) :
					false;
	}
	
	/**
	 * Getter for 'translatedFrom' language
	 * 
	 * @return String
	 */
	public String getTranslatedFrom() {
		return translatedFrom;
	}
	
	/**
	 * Setter for 'translatedFrom' language
	 * 
	 * @param translatedFrom
	 */
	public void setTranslatedFrom(String translatedFrom) {
		this.translatedFrom = translatedFrom;
	}
	
	/**
	 * Getter for 'series'
	 * 
	 * @return String
	 */
	public String getSeries() {
		return series;
	}
	
	/**
	 * Setter for 'series'
	 * 
	 * @param series
	 */
	public void setSeries(String series) {
		this.series = series;
	}
	
	/**
	 * Getter for 'supplements'
	 * 
	 * @return String
	 */
	public String getSupplements() {
		return supplements;
	}
	
	/**
	 * Setter for 'supplements'
	 * 
	 * @param supplements
	 */
	public void setSupplements(String supplements) {
		this.supplements = supplements;
	}
	
	/**
	 * Getter for 'footNote'
	 * 
	 * @return String
	 */
	public String getFootNote() {
		return footNote;
	}
	
	/**
	 * Setter for 'footNote'
	 * 
	 * @param footNote
	 */
	public void setFootNote(String footNote) {
		this.footNote = footNote;
	}
	
	/**
	 * Getter for 'authors'
	 * 
	 * @return List
	 */
	public List<String> getAuthors() {
		return authors;
	}
	
	/**
	 * This method is returning a String that contains all
	 * comma separated authors from the list of authors.
	 * 
	 * @return String
	 */
	public String getAuthorsAsString(){
		if(authors.isEmpty() || authors == null) return "";
		
		StringBuilder sb = new StringBuilder();
		Iterator<String> iter = authors.iterator();
		while(iter.hasNext()){
			String s = iter.next();
			if(!s.equals("")){
				sb.append(s);
				if(iter.hasNext()){
					sb.append(", ");
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * This method is setting authors from the list passed as
	 * a parameter. If existing list of authors is not empty,
	 * than existing list is cleared first and than new authors
	 * are added to the existing empty list.
	 * 
	 * @param authors
	 */
	public void setAuthors(List<String> authors) {
		if(this.authors.isEmpty()){
			this.authors.addAll(authors);
		} else {
			this.authors.clear();
			this.authors.addAll(authors);
		}
	}
	
	/**
	 * This methods is setting authors to the list of authors passed
	 * as a single line comma separated String object.
	 * 
	 * @param authors
	 */
	public void setAuthorsFromString(String authors) {
		if(authors != null){
			List<String> list = Arrays.asList(authors.split("\\s*,\\s*"));
			
			Iterator<String> iter = list.iterator();
			while(iter.hasNext()){
				String s = iter.next();
				if(!s.equals("")){
					this.authors.add(s);
				}
			}
		}
	}
	
	/**
	 * This method adds specified author to the list of authors.
	 * 
	 * @param author
	 * @return boolean
	 */
	public boolean addAuthor(String author) {
		if(author == null) return false;
		
		return (!authors.contains(author)) ? authors.add(author) : false;
	}
	
	/**
	 * This method removes specified author to the list of authors.
	 * 
	 * @param author
	 * @return boolean
	 */
	public boolean removeAuthor(String author) {
		return (author != null) ?
				(authors.contains(author)) ?
						authors.remove(author) :
							false :
								false;
	}
	
	/**
	 * Getter for 'edition'
	 * 
	 * @return int
	 */
	public int getEdition() {
		return edition;
	}
	
	/**
	 * Setter for 'edition'
	 * 
	 * @param edition
	 */
	public void setEdition(int edition) {
		this.edition = edition;
	}
	
	/**
	 * Getter for book 'publicationDate'
	 * 
	 * @return Calendar
	 */
	public Calendar getPublicationDate() {
		return publicationDate;
	}
	
	/**
	 * Setter for book 'publicationDate'
	 * 
	 * @param publicationDate
	 */
	public void setPublicationDate(Calendar publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	/**
	 * Setter for book 'publicationPlace'
	 * 
	 * @param publicationPlace
	 */
	public void setPublicationPlace(String publicationPlace) {
		this.publicationPlace = publicationPlace;
	}
	
	/**
	 * Getter for book 'publicationPlace'
	 * 
	 * @return String
	 */
	public String getPublicationPlace() {
		return publicationPlace;
	}
	
	/**
	 * Getter for 'publisher'
	 * 
	 * @return String
	 */
	public String getPublisher() {
		return publisher;
	}
	
	/**
	 * Setter for 'publisher'
	 * 
	 * @param publisher
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	/**
	 * Getter for 'pagination'
	 * 
	 * @return int
	 */
	public int getPagination() {
		return pagination;
	}
	
	/**
	 * Setter for 'pagination'
	 * 
	 * @param pagination
	 */
	public void setPagination(int pagination) {
		this.pagination = pagination;
	}
	
	/**
	 * Getter for book 'format'
	 * 
	 * @return String
	 */
	public String getFormat() {
		return format;
	}
	
	/**
	 * Setter for book 'format'
	 * 
	 * @param format
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	
	/**
	 * Getter for 'editor'
	 * 
	 * @return String
	 */
	public String getEditor() {
		return editor;
	}
	
	/**
	 * Setter for 'editor'
	 * 
	 * @param editor
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}
	
	/**
	 * This method returns only year from book 'publicationDate'
	 * 
	 * @return String
	 */
	public String getYear(){
		return (publicationDate != null) ?
				BasicLibraryDateFormatter.formatDateToYear(publicationDate) :
					null;
	}
	
	/**
	 * Getter for 'genres'
	 * 
	 * @return List
	 */
	public List<String> getGenres() {
		return genres;
	}
	
	/**
	 * This method is returning a String that contains all
	 * comma separated genres from the list of genres.
	 * 
	 * @return String
	 */
	public String getGenresAsString(){
		if(genres.isEmpty() || genres == null) return "";
		
		StringBuilder sb = new StringBuilder();
		Iterator<String> iter = genres.iterator();
		while(iter.hasNext()){
			String s = iter.next();
			if(!s.equals("")){
				sb.append(s);
				if(iter.hasNext()){
					sb.append(", ");
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * This method is setting genres from the list passed as
	 * a parameter. If existing list of genres is not empty,
	 * than existing list is cleared first and than new genres
	 * are added to the existing empty list.
	 * 
	 * @param genres
	 */
	public void setGenres(List<String> genres) {
		if(this.genres.isEmpty()){
			this.genres.addAll(genres);
		} else {
			this.genres.clear();
			this.genres.addAll(genres);
		}
	}
	
	/**
	 * This methods is setting genres to the list of genres passed
	 * as a single line comma separated String object.
	 * 
	 * @param genres
	 */
	public void setGenresFromString(String genres) {
		if(genres != null){
			List<String> list = Arrays.asList(genres.split("\\s*,\\s*"));
			
			Iterator<String> iter = list.iterator();
			while(iter.hasNext()){
				String s = iter.next();
				if(!s.equals("")){
					this.genres.add(s);
				}
			}
		}
	}
	
	/**
	 * This method adds specified genre to the list of genres.
	 * 
	 * @param genre
	 * @return boolean
	 */
	public boolean addGenre(String genre) {
		return (genre != null) ?
				(!genres.contains(genre)) ?
						genres.add(genre) :
							false :
								false;
	}
	
	/**
	 * This method removes specified genre from the list of genres.
	 * 
	 * @param genre
	 * @return boolean
	 */
	public boolean removeGenre(String genre) {
		return (genre != null) ?
				(genres.contains(genre)) ?
						genres.remove(genre) :
							false :
								false;
	}
	
	/**
	 * Getter for 'keywords'
	 * 
	 * @return List
	 */
	public List<String> getKeywords() {
		return keywords;
	}
	
	/**
	 * This method is returning a String that contains all
	 * comma separated keywords from the list of keywords.
	 * 
	 * @return String
	 */
	public String getKeywordsAsString(){
		if(keywords.isEmpty() || keywords == null) return "";
		
		StringBuilder sb = new StringBuilder();
		Iterator<String> iter = keywords.iterator();
		while(iter.hasNext()){
			String s = iter.next();
			if(!s.equals("")){
				sb.append(s);
				if(iter.hasNext()){
					sb.append(", ");
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * This method is setting keywords from the list passed as
	 * a parameter. If existing list of keywords is not empty,
	 * than existing list is cleared first and than new keywords
	 * are added to the existing empty list.
	 * 
	 * @param keywords
	 */
	public void setKeywords(List<String> keywords) {
		if(this.keywords.isEmpty()){
			this.keywords.addAll(keywords);
		} else {
			this.keywords.clear();
			this.keywords.addAll(keywords);
		}
	}
	
	/**
	 * This methods is setting keywords to the list of keywords passed
	 * as a single line comma separated String object.
	 * 
	 * @param keywords
	 */
	public void setKeywordsFromString(String keywords) {
		if(keywords != null){
			List<String> list = Arrays.asList(keywords.split("\\s*,\\s*"));
			
			Iterator<String> iter = list.iterator();
			while(iter.hasNext()){
				String s = iter.next();
				if(!s.equals("")){
					this.keywords.add(s);
				}
			}
		}
	}
	
	/**
	 * Adding keyword to the list of keywords.
	 * 
	 * @param keyword
	 * @return boolean
	 */
	public boolean addKeyword(String keyword) {
		return (keyword != null) ?
				(!keywords.contains(keyword)) ?
						keywords.add(keyword) :
							false :
								false;
	}
	
	/**
	 * Removes specified keyword from the list of keywords.
	 * 
	 * @param keyword
	 * @return boolean
	 */
	public boolean removeKeyword(String keyword) {
		return (keyword != null) ?
				(keywords.contains(keyword)) ?
						keywords.remove(keyword) :
							false :
								false;
	}
	
	/**
	 * Getter for 'subtitle'
	 * 
	 * @return String
	 */
	public String getSubtitle() {
		return subtitle;
	}
	
	/**
	 * Setter for 'subtitle'
	 * 
	 * @param subtitle
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	/**
	 * Getter for 'bookID'
	 * 
	 * @return long
	 */
	public long getBookID() {
		return bookID;
	}
	
	/**
	 * Setter for 'bookID'
	 * 
	 * @param bookID
	 */
	public void setBookID(long bookID) {
		this.bookID = bookID;
	}
	
	/**
	 * Getter for 'title'
	 * 
	 * @return String
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Setter for 'title'
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Getter for 'language'
	 * 
	 * @return String
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * Setter for 'language'
	 * 
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * Getter for 'description'
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Setter for 'description'
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Getter for 'dateOfEntry' to database
	 * 
	 * @return Calendar
	 */
	public Calendar getDateOfEntry() {
		return dateOfEntry;
	}
	
	/**
	 * Setter for 'dateOfEntry' to database
	 * 
	 * @param dateOfEntry
	 */
	public void setDateOfEntry(Calendar dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}
	
	/**
	 * Getter for book acquired 'price'
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getPrice() {
		return price;
	}
	
	/**
	 * Setter for book acquired 'price'
	 * 
	 * @param price
	 */
	public void setPrice(BigDecimal price) {
		this.price = (price != null) ?
				price.setScale(2, RoundingMode.HALF_EVEN) :
					new BigDecimal("0.00");
	}
	
	/**
	 * Getter for book 'isbn'
	 * 
	 * @return String
	 */
	public String getIsbn() {
		return isbn;
	}
	
	/**
	 * Setter for book 'isbn'
	 * 
	 * @param isbn
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	/**
	 * Getter for book 'condition'
	 * 
	 * @return String
	 */
	public String getCondition() {
		return condition;
	}
	
	/**
	 * Setter for book 'condition'
	 * 
	 * @param condition
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	/**
	 * Getter for book 'generalStatus'
	 * 
	 * @return ItemStatus
	 */
	public ItemStatus getStatus() {
		return status;
	}
	
	/**
	 * This method is checking if book's 'generalStatus' set to DISPOSED
	 * and returns TRUE if so or FALSE if generalStatus is different.
	 * 
	 * @return boolean
	 */
	public boolean isStatusDisposed(){
		return (status == ItemStatus.DISPOSED) ? true : false;
	}
	
	/**
	 * This method is checking if book's 'generalStatus' set to ONLOAN
	 * and returns TRUE if so or FALSE if generalStatus is different.
	 * 
	 * @return boolean
	 */
	public boolean isStatusOnLoan(){
		return (status == ItemStatus.ONLOAN) ? true : false;
	}
	
	/**
	 * This method is checking if book's 'generalStatus' set to ONSHELF
	 * and returns TRUE if so or FALSE if generalStatus is different.
	 * 
	 * @return boolean
	 */
	public boolean isStatusOnShelf(){
		return (status == ItemStatus.ONSHELF) ? true : false;
	}
	
	/**
	 * This method is checking if book's 'generalStatus' set to RESERVED
	 * and returns TRUE if so or FALSE if generalStatus is different.
	 * 
	 * @return boolean
	 */
	public boolean isStatusReserved(){
		return (status == ItemStatus.RESERVED) ? true : false;
	}
	
	/**
	 * This method is checking if book's 'generalStatus' set to UNKNOWN
	 * and returns TRUE if so or FALSE if generalStatus is different.
	 * 
	 * @return boolean
	 */
	public boolean isStatusUnknown(){
		return (status == ItemStatus.UNKNOWN) ? true : false;
	}
	
	/**
	 * Setter for book 'generalStatus'
	 * 
	 * @param generalStatus
	 */
	public void setStatus(ItemStatus status) {
		this.status = status;
	}
	
	/**
	 * toString() method returns Harvard style reference string as follow:
	 * 	if no authors && no editor:
	 *		"The University Encyclopedia (1985) London: Roydon."
	 *	if no authors && editor:
	 *		"Danaher, P. (ed.) (1998) Beyond the ferris wheel, Rockhampton: CQU Press."
	 *	if 1 author:
	 *		"Adair, J. (1988) Effective time management: How to save time and spend it wisely, London: Pan Books."
	 *	if 2 authors:
	 *		"McCarthy, P. and Hatcher, C. (1996) Speaking persuasively: Making the most of your presentations, Sydney: Allen and Unwin."
	 *	if 3 or more authors:
	 *		"Fisher, R., Ury, W. and Patton, B. (1991) Getting to yes: Negotiating an agreement without giving in, 2nd edition, London: Century Business."
	 *	if edition >= 2
	 *		"Barnes, R. (1995) Successful study for degrees, 2nd edition, London: Routledge."
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\"");
		// Checking the number of authors
		if(!authors.isEmpty()){
			int a = this.authors.size();
        switch (a) {
        	case 0: break;    
        	case 1: sb.append(authors.get(0) + " ");
        		sb.append("(" + this.getYear() + ") " + title + ", ");
        		break;
        	case 2: sb.append(authors.get(0));
        		sb.append(" and ");
        		sb.append(authors.get(1) + " ");
        		sb.append("(" + this.getYear() + ") " + title + ", ");
        		break;
        	default: sb.append(authors.get(0));
        		sb.append(", ");
        		sb.append(authors.get(1));
        		sb.append(" and ");
        		sb.append(authors.get(2) + " ");
        		sb.append("(" + this.getYear() + ") " + title + ", ");
        		break;
        	}
		} else if (!this.editor.isEmpty()){
			// If no authors set, than editor is printed
			sb.append(editor + " (ed.) ");
			sb.append("(" + this.getYear() + ") " + title + ", ");
		} else {
			// If no authors or editors set
			sb.append(title + ", " + "(" + this.getYear() + ") ");
		}
		// Checking if edition is not first
		if(this.edition > 1){
			sb.append(edition + " edition, ");
		}
		if(!this.publicationPlace.isEmpty()){
			sb.append(publicationPlace +": ");
		}
		if (!this.publisher.isEmpty()){
			sb.append(publisher + ".\"");
		}
		
		return sb.toString();
	}
}