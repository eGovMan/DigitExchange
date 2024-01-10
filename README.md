DIGIT Exchange

1. Administrator Regisgration and Login
- Create default administrator and pin during server start if not already created. Admin passcode is displayed on console logs. 
- Verify administrator phone and email on first login.
2. Setup Protocol, Organisation Type, Message Formats (Nouns) and Contracts 
- Administrator can set up protocol. For each protocol, can setup list of organisation roles. For each role, administrator can configure what message organisation role can send to another organisation role and should accept. Additionally, one can configure the contract e.g. SLA for response. 
3. Setup Server Organisation
- Administrator will configure the type of organisation which is hosting the DIGIT Exchange Server
4. Register Message Processors
- Administrator can configure message processors - these are internal systems that will process and send replies to DIGIT Exchange. Internal systems are configured based on protocol and message type. 
5. Add Individual 
- Administrator can add other individuals who are administrator of other DIGIT Exchange Servers.
6. Verify Individual i.e. Other DIGIT Exchange Administrators.  
- Individual need to verify phone and email one first login. 
7. Register Organisation
- Verified Individual (or DIGIT Exchange Administrators) can register their organisation for a given role by providing url of their DIGIT Exchange Server and API Keys.
- DIGIT Exchange to pull public key and verify connection with external DIGIT Exchange Server. 
- First time, secure key exchange is done during this process.
8. Send Message
- Internal Systems can now send message to DIGIT Exchange
- DIGIT Exchange signs the message and encrypts is based on the key exchanged with the target DIGIT Exchange Server. 
9. Receive Message
- On receiving a message from another DIGIT Exchange Server, it checks the signature, decrypts the message and then routes it to the internal system based on the message type. 