package javasoap.book.ch8;

public class Listing {

   String _make;
   String _model;
   int    _year;
   int    _miles;
   String _ownerName;
   String _ownerEmail;

   public Listing() {
   }

   public Listing(String make, String model,
     int year, int miles, String ownerName, String ownerEmail) {

      setMake(make);
      setModel(model);
      setYear(year);
      setMiles(miles);
      setOwnerName(ownerName);
      setOwnerEmail(ownerEmail);
   }

   public void setMake(String make) {
      _make = make;
   }

   public String getMake() {
      return _make;
   }

   public void setModel(String model) {
      _model = model;
   }

   public String getModel() {
      return _model;
   }

   public void setYear(int year) {
      _year = year;
   }

   public int getyear() {
      return _year;
   }

   public void setMiles(int miles) {
      _miles = miles;
   }

   public int getMiles() {
      return _miles;
   }

   public void setOwnerName(String ownerName) {
      _ownerName = ownerName;
   }

   public String getOwnerName() {
      return _ownerName;
   }

   public void setOwnerEmail(String ownerEmail) {
      _ownerEmail = ownerEmail;
   }

   public String getOwnerEmail() {
      return _ownerEmail;
   }

   public String toString() {
      String result = String.valueOf(_year) + " " +
                      _make + " " + _model +
                      " with " + String.valueOf(_miles) + 
                      " miles:" +
                      "\n   Owned by " + _ownerName + "(" +
                      _ownerEmail + ")";
      return result;
   }
}
