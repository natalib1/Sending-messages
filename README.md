# Sending-messages
client-server program

JAVA

A messaging system that allows the administrator to send messages to customers registered with the server.

Server Plan -
-Listens at Port 6666 and receives inquiries from customers interested in signing up for messages and inquiries from customers who wish to remove themselves from the list.
-The program allows the administrator to enter messages (in a text area) and distributes the messages to all customers registered with it.
-The server program does not store the old messages, each message is distributed to customers who are registered at the moment.

Customer Plan -
-A customer can contact the server to sign up for messages or to remove himself from the list.
-Messages that reach the customer are displayed in a text area along with the date and time they arrived.
-The user can clear the text area using the clear button.

The system is implemented using UDP communication.
