# User Stories: Binärumrechner Tab

## Epic: Binärumrechner als neuer Tab hinzufügen

### Story 1: Main.java erweitern für Tab-Navigation
**Als** Benutzer  
**möchte ich** zwischen Standard Calculator und Binärumrechner wechseln können  
**damit** ich beide Funktionen in einer Anwendung nutzen kann.

#### Akzeptanzkriterien:
- [ ] JTabbedPane in Main.java hinzufügen
- [ ] Tab "Calculator" mit bestehendem Calculator (unverändert)
- [ ] Tab "Binärumrechnung" für neuen Binary Calculator
- [ ] Bestehende Calculator-Funktionalität bleibt 100% unverändert
- [ ] FlatDarculaLaf Design für beide Tabs

#### Subtasks:
- [ ] Main.java um JTabbedPane erweitern
- [ ] Bestehenden Calculator in Tab integrieren (ohne Änderungen)
- [ ] Zweiten Tab für Binary Calculator vorbereiten
- [ ] Tab-Layout und -Styling implementieren

---

### Story 2: BinaryCalculatorUI erstellen
**Als** Benutzer  
**möchte ich** einen Binärumrechner mit zwei Displays haben  
**damit** ich Dezimal- und Binärzahlen parallel sehen kann.

#### Akzeptanzkriterien:
- [ ] Zwei JTextField Displays: "Dezimal" und "Binär" mit Labels
- [ ] Dezimal-Display: normale Zahleneingabe
- [ ] Binär-Display: zeigt Binärzahl in 4er-Blöcken (z.B. "1010 1100")
- [ ] Font-Styling wie Standard Calculator (Arial, Bold, 24)
- [ ] Real-time Konvertierung zwischen beiden Displays

#### Subtasks:
- [ ] BinaryCalculatorUI Klasse als JPanel erstellen
- [ ] Dual-Display Layout implementieren
- [ ] Labels "Dezimal:" und "Binär:" hinzufügen
- [ ] Display-Styling vom Calculator übernehmen
- [ ] Real-time Update-Logik zwischen Displays

---

### Story 3: Button Panel für Binärrechner
**Als** Benutzer  
**möchte ich** ein vertrautes Taschenrechner-Layout mit eingeschränkten Buttons haben  
**damit** nur relevante Funktionen verfügbar sind.

#### Akzeptanzkriterien:
- [ ] GridLayout(5,4) wie Standard Calculator
- [ ] Nur Buttons 0, 1, CE, C, ← sind aktiv/klickbar
- [ ] Buttons 2-9, +/-, ., =, +, -, ×, ÷ sind ausgegraut/deaktiviert
- [ ] <> Button ersetzt eine deaktivierte Taste für Modus-Umschaltung
- [ ] Konsistente Button-Größe und Font wie Standard Calculator

#### Subtasks:
- [ ] Button Panel mit GridLayout(5,4) erstellen
- [ ] Button-Array mit allen Bezeichnungen definieren
- [ ] Aktive Buttons (0,1,CE,C,←,<>) implementieren
- [ ] Inaktive Buttons ausgegraut darstellen
- [ ] Button-Styling vom Calculator übernehmen

---

### Story 4: BinaryCalculator Logic Klasse
**Als** Entwickler  
**möchte ich** eine BinaryCalculator Klasse für die Konvertierungslogik haben  
**damit** die Business-Logic gekapselt ist.

#### Akzeptanzkriterien:
- [ ] Ähnliche Struktur wie Calculator.java
- [ ] enterNumber() mit Validation (nur 0,1 im Binary Mode, 0-9 im Decimal Mode)
- [ ] Modus-Umschaltung zwischen DECIMAL und BINARY Input
- [ ] getBinaryString() und getDecimalString() Methoden
- [ ] clearAll(), clearCurrent(), backspace() Funktionen

#### Subtasks:
- [ ] BinaryCalculator.java im basic Package erstellen
- [ ] Input-Mode Enum (DECIMAL, BINARY) definieren
- [ ] Eingabe-Validation je nach Modus implementieren
- [ ] Dezimal ↔ Binär Konvertierungsalgorithmen
- [ ] Standard Calculator-Methoden (clear, backspace) adaptieren

---

### Story 5: Modus-Umschaltung mit <> Taste
**Als** Benutzer  
**möchte ich** zwischen Dezimal- und Binär-Eingabemodus wechseln können  
**damit** ich flexibel in beiden Zahlensystemen eingeben kann.

#### Akzeptanzkriterien:
- [ ] <> Button schaltet zwischen DECIMAL und BINARY Eingabemodus
- [ ] DECIMAL Modus: Eingabe geht ins Dezimal-Display, 0-9 Buttons aktiv
- [ ] BINARY Modus: Eingabe geht ins Binär-Display, nur 0,1 Buttons aktiv
- [ ] Visueller Indikator zeigt aktuellen Modus (z.B. Button-Hervorhebung)
- [ ] Automatische Konvertierung und Display-Update bei Modus-Wechsel

#### Subtasks:
- [ ] <> Button ActionListener implementieren
- [ ] Modus-State in BinaryCalculator verwalten
- [ ] Button-Enable/Disable Logik je nach Modus
- [ ] Visual Mode Indicator (Button-Styling oder Label)
- [ ] Automatische Konvertierung bei Modus-Switch

---

### Story 6: 4er-Block Binär-Formatierung
**Als** Benutzer  
**möchte ich** Binärzahlen in lesbaren 4er-Blöcken angezeigt bekommen  
**damit** große Binärzahlen übersichtlich sind.

#### Akzeptanzkriterien:
- [ ] Binärzahlen werden automatisch in 4er-Gruppen formatiert
- [ ] Beispiel: "101011001110" → "1010 1100 1110"
- [ ] Formatierung erfolgt in Real-time bei Eingabe
- [ ] Führende Nullen optional für vollständige Blöcke
- [ ] Konsistente Leerzeichen zwischen Blöcken

#### Subtasks:
- [ ] formatBinaryDisplay() Utility-Methode
- [ ] Real-time Formatierung bei Display-Updates
- [ ] Leading-Zero Logik implementieren
- [ ] String-Manipulation für 4er-Gruppierung

---

### Story 7: Input Handling und Validation
**Als** Benutzer  
**möchte ich** nur gültige Eingaben machen können  
**damit** Fehler vermieden werden.

#### Akzeptanzkriterien:
- [ ] BINARY Modus: nur 0,1 Eingaben werden akzeptiert
- [ ] DECIMAL Modus: nur 0-9 Eingaben werden akzeptiert
- [ ] Ungültige Tasten werden ignoriert (keine Fehlermeldung)
- [ ] Backspace, Clear-Funktionen arbeiten in beiden Modi
- [ ] Maximale Eingabelänge begrenzt (z.B. 32 Bit)

#### Subtasks:
- [ ] Input-Validation in enterNumber() implementieren
- [ ] Modus-spezifische Eingabe-Filter
- [ ] Silent Rejection ungültiger Eingaben
- [ ] Max-Length Validation
- [ ] Clear-Funktionen für beide Modi

---

### Story 8: Error Handling
**Als** Benutzer  
**möchte ich** bei Problemen visuelles Feedback bekommen  
**damit** ich weiß, was passiert ist.

#### Akzeptanzkriterien:
- [ ] "Error" Anzeige bei Overflow oder ungültigen Werten
- [ ] Rot-Highlighting wie im Standard Calculator
- [ ] Graceful Recovery nach Fehlern
- [ ] Keine Crashes bei ungültigen Operationen

#### Subtasks:
- [ ] Error-Display Mechanismus implementieren
- [ ] Rot-Highlighting für Error-State
- [ ] Error-Recovery und Reset-Logik
- [ ] Exception-Handling für Konvertierungsfehler

---

## Testing Stories

### Story 9: BinaryCalculator Unit Tests
**Als** Entwickler  
**möchte ich** umfassende Tests für die Binärrechner-Logik haben  
**damit** alle Konvertierungen korrekt funktionieren.

#### Akzeptanzkriterien:
- [ ] BinaryCalculatorTest.java im test Package
- [ ] Tests für Dezimal-zu-Binär Konvertierung
- [ ] Tests für Binär-zu-Dezimal Konvertierung
- [ ] Edge-Cases: 0, große Zahlen, maximale Werte
- [ ] Input-Validation Tests für beide Modi
- [ ] Modus-Switching Tests

#### Subtasks:
- [ ] Test-Klasse mit JUnit 5 erstellen
- [ ] Konvertierungs-Tests implementieren
- [ ] Input-Validation Test-Cases
- [ ] Mode-Switching Behavior Tests
- [ ] Edge-Case und Boundary Tests

### Story 10: BinaryCalculatorUI Component Tests
**Als** Entwickler  
**möchte ich** die UI-Komponenten des Binärrechners testen  
**damit** die Benutzerinteraktion zuverlässig funktioniert.

#### Akzeptanzkriterien:
- [ ] Button-Click Tests für aktive Buttons (0,1,CE,C,←,<>)
- [ ] Display-Update Tests bei Eingaben
- [ ] Button-Enable/Disable Tests je nach Modus
- [ ] Formatierung Tests für 4er-Block Binär-Display
- [ ] Mode-Switch UI-Behavior Tests

#### Subtasks:
- [ ] UI-Test Framework Setup
- [ ] Button-Interaction Tests
- [ ] Display-Update und Formatierung Tests
- [ ] Mode-Switch Visual Tests
- [ ] Error-Display Tests

### Story 11: Integration Tests
**Als** Entwickler  
**möchte ich** sicherstellen, dass der Binärrechner-Tab korrekt integriert ist  
**damit** die gesamte Calculator Suite stabil funktioniert.

#### Akzeptanzkriterien:
- [ ] Tab-Switching funktioniert ohne Probleme
- [ ] Standard Calculator bleibt unverändert funktional
- [ ] Binärrechner-Tab ist vollständig funktional
- [ ] Keine Performance-Probleme bei Tab-Wechsel
- [ ] Memory-Management ist korrekt

#### Subtasks:
- [ ] Tab-Integration Tests
- [ ] Cross-Tab State-Isolation Tests
- [ ] Performance Tests für Tab-Switching
- [ ] Memory-Leak Detection
- [ ] End-to-End Functionality Tests
