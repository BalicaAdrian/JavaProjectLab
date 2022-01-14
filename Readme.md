# Gestiune medicala
## _The Last Markdown Editor, Ever_

Proiectul consta in 8 tabele (cu tot cu ce de legatura). Acestea sunt Patient, Disease, Meds (tabela asociativa medicamentation), Account, Appointment, Doctor, Payment.

Logica dintre tabele este urmatoarea, Pacientul poate avea mai multe Disease iar diseaseul poate avea mai multe medicamente, care la randul sau pot face parte din mai multe diseases. De asemenea pacientul poate avea un cont, poate crea o programare, la care i se va adauga ulterior un doctor.
Se pot face payment-uri intre un doctor si pacient fiind modificate valorile din contrurile lor bazate pe plati.

Patien 1:1 account, Patient 1:M Appointment, Patient 1:M Disease, Disease M:m Meds, Account 1:1 Doctor, Payment 1:1 Account (in payment avem 2 accounts)
Cas si utilizator al aplicatiiei poti:


- Sa creezi un pacient, sa ii updatezi campurile, sa il stergi (la stergere se vor sterge toate legaturile pe care acesta le are, acest lucru se intampla la toate entitatile mai putin la account unde acesta va ramane in db)
- Se pot vizualiza pacientii cu toate detaliile acestora
- Se poate crea un singur cont pentru un doctor si unul singur pentru pacient
- Se poate adauga un disease pentru un patient care are un pret de pornire(de tratare)
- La pretul disease-ului se va adauga pretul medicamentelor care vor fi atasate acestuia (o combinatie de pret*cantitate)
- La momnetul stergerii unui medicament/ se va recalcula tratamentul in care se afla (se adauga valaorea medicamentelor)
- Adaugarea unui medicament se va face pe baza de nume si cantitate.(practic se verifica existenta deja a combinatiei medicament cantitate)
- Se poate crea un appointment pentru un pacient, sa se inchida/finalizeaze, sau sa se anuleze
- se pot vizualiza toate appointmenturile unui doctor, a unui pacient
- se pot face plati din contul unui pacient catre contul unui doctor, si se poate face undo la o anumite plata banii fiind returnati intre cei doi (se poate face o singura data)
- Se pot elimina medicamente din tratament (fiind automat recaulcuat costul)
- Se pot edita oricare dintre entitatile create
- Vizualizari separate a tuturor entitatile bazate pe id (pentur a gestiona din fe diferite nevoi)

