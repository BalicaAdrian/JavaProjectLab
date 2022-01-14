# Gestiune medicala
## _The Last Markdown Editor, Ever_

Proiectul consta in 8 tabele (cu tot cu ce de legatura). Acestea sunt Patient, Disease, Meds (tabela asociativa medicamentation), Account, Appointment, Doctor, Payment.

Logica dintre tabele este urmatoarea, Pacientul poate avea mai multe Disease iar diseaseul poate avea mai multe medicamente, care la randul sau pot face parte din mai multe diseases. De asemenea pacientul poate avea un cont, poate crea o programare, la care i se va adauga ulterior un doctor.
Se pot face payment-uri intre un doctor si pacient fiind modificate valorile din contrurile lor bazate pe plati.

Cas si utilizator al aplicatiiei poti:


- Sa creezi un pacient, sa ii updatezi campurile, sa il stergi (la stergere i se vor sterge toate legaturile pe care acesta le are)
- Se pot vizualiza pacientii cu toate detaliile acestora
- Se poate adauga un disease pentru un patient care are un pret de pornire(de tratare)
- La pretul disease-ului se vor adauga pretul medicamentelor care vor fi atasate acestuia (o combinatie de pret*cantitate)
- La momnetul stergerii unui medicament/ se va recalcula tratamentul in care se afla (se adauga valaorea medicamentelor)
- La adaugarea unui medicament se vor cauta pe baza de pret si cantitate.
- Se poate crea un appointment pentru un pacient, sa se inchida/finalizeaze, sau sa se anuleze
- se pot vizualiza toate appointmeturile unui docor
- se pot face plati din contrul unui pacient catre contul unui doctor, si se poate face undo la o anumite plata banii fiind returnati intre cei doi (se poate face o singura data)
- -medicamentele sunt cautate dupa nume si cantitate (nu se poate adauga un nou medicament cu acelas nume si cantitate ca s anu avem date redundante)
- Se pot elimina medicamente din tratament (fiind automat recaulcuat costul)



