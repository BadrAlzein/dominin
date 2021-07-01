# Bildschirm nicht mehr resizeable machen

**User story:**einfügen eines responsive Design der Anwendung/DOM 46

Der Nutzer kann nicht mehr an den Fensterrändern das Fenster größer oder kleiner machen.

## Considered Alternatives

* resizeable lassen und Element an Ränder anheften
* resizeable lassen und für unterschiedliche Auflösungen unterschidliche FXML
* resizeable ausschalten und über Einstellungen Auflösung einstellbar machen

## Decision Outcome

* **Chosen Alternative:** resizeable ausschalten und über Einstellungen Auflösung einstellbar machen
* **Rationale:** dadurch muss für jede Auflösung nur eine Zeile hinzugefügt werden, anstatt einer ganzen FXML