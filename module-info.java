module Game1 {
    requires java.desktop;
    /**
     * Konrad Pempera
     * Celem projektu było oprogramowanie gry "odkrywcy skarbow"
     * Gra polega na zebraniu skarbów o jak największej wartości
     * w grze uczestniczą znani intelgentni gracze, komunkujący się z serwerem gry za pomocą Socketów
     * Gracze loguja się na określony port serwera gry, a w odpowiedzi dostają indywidualny port, na który w kolejnych etapach będą wysyłać żądania
     * Serwer odsyła komunikaty na określony port i określoną nazwę hosta(gracza) przesłaną przez gracza podczas logowania
     * Serwer gry zapewnia dostęp graczom do mapy gry(skarbów, wolnych przestrzeni), w kolejności zgłoszeń komunikatów
     * Serwer daje możliwość dostępu do skarbu, wolnego miejsca tylko jednemu graczowi, nie jest możliwe by gracze "wchodzili na siebie".
     * Gracze mają wbudowane algorytmy wyszukiwania najkrótszej drogi do wszystkich widocznych dla nich skarbów oraz nieodkrytych pól na planszy,
     * w pierwszej kolejności gracz kieruje się w stronę pola z skarbem, natomiast gdy takego nie widzi, skieruje się w stronę nieodkrytych dotąd pól.
     * Podczas odkrycia wielu skarbów na raz gracz udaje się do skarbu o największym współczynniku wartości do czasu potrzebnego na jego wydobycie i dotarcie do niego.
     * Pozycja graczy oraz skarbów oraz ich wartości i czasu wydobycia, generowane są losowo na początku gry, podczas uruchomiena serwera
     * Rozpoczęcie gry odbywa się po 15 sekundach od uruchomienia serwera, do tego momentu wszyscy gracze powinni już być zalogowani.
     * Oczekiwanie związane z wydobyciem skarbu, wspólne wystartowanie wszystkich graczy odbywa się poprzez nieodsyłanie odpowiedzi przez serwer do pewnej chwili
     * Projekt składa się z dwóch widoków
     * 1) widok gracza
     * 2) widok serwera i wyników graczy aktualizowanych na bieżąco
     * Ostateczny wynik gry przesyłany jest do wszystkich graczy, gdy ci odkryją już całą mapę na ich żądanie
     * Aplikacja została napisana w Java Swing.
     * aplikacja została skompliwona za pomocą wywołania z katalogu gdzie znajuje się aplikacja następujących poleceń:
     * javac -d bin src\module-info.java src\gamer\Gamer.java src\gamer\GamerController.java src\gamer\GamerFrame.java src\gamer\VisualisationPanel.java src\model\GameMap.java
     * src\model\MessageQueue.java src\model\MessageWithAccountQueue.java src\model\MessageWithAccount.java src\model\Possition.java
     * src\model\SocketListenerWithAccount.java src\model\Treassure.java src\server\Server.java src\server\ServerController.java src\server\ServerFrame.java
     * src\server\Statistic.java src\server\VisualisationServerPanel.java src\SocketPackage\SocketSender.java src\threads\GamerListenerWithQueue.java
     * src\threads\GamerThread.java src\threads\LoginAtServerListener.java src\threads\ServerListenerWithQueue.java src\threads\ServerThread.java
     * src\tools\GamerAccount.java src\tools\GamerMenager.java*/
}