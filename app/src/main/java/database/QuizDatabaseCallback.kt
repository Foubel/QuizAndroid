// QuizDatabaseCallback.kt

package database

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizDatabaseCallback(
    private val context: Context,
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        scope.launch(Dispatchers.IO) {
            populateDatabase(context)
        }
    }

    private suspend fun populateDatabase(context: Context) {
        // Obtenir l'instance de la base de données
        val quizDatabase = Quiz.getInstance(context)

        // Insérer les catégories
        val category1 = Category(name = "Jeux-vidéos")
        val category2 = Category(name = "Cinéma")
        val category3 = Category(name = "Géographie")
        val category4 = Category(name = "Histoire")
        val category5 = Category(name = "Musique")
        val category6 = Category(name = "Science")

        // Logs pour vérifier l'insertion
        Log.d("DB_INIT", "Inserting categories")

        val categoryId1 = quizDatabase.categoryDao().insertCategory(category1).toInt()
        val categoryId2 = quizDatabase.categoryDao().insertCategory(category2).toInt()
        val categoryId3 = quizDatabase.categoryDao().insertCategory(category3).toInt()
        val categoryId4 = quizDatabase.categoryDao().insertCategory(category4).toInt()
        val categoryId5 = quizDatabase.categoryDao().insertCategory(category5).toInt()
        val categoryId6 = quizDatabase.categoryDao().insertCategory(category6).toInt()

        // Logs pour vérifier l'insertion
        Log.d("DB_INIT", "Categories inserted with IDs: $categoryId1, $categoryId2, $categoryId3, $categoryId4, $categoryId5, $categoryId6")

        // Insérer les questions pour chaque catégorie
        val questions = listOf(
            // Questions pour "Jeux-vidéos"
            Question(text = "Quel est le jeu vidéo le plus vendu de tous les temps ?", choices = listOf("Tetris", "Minecraft", "GTA V", "Wii Sports"), correctAnswer = 3, categoryId = categoryId1),
            Question(text = "En quelle année est sorti le premier jeu Super Mario ?", choices = listOf("1983", "1985", "1987", "1990"), correctAnswer = 1, categoryId = categoryId1),
            // Ajoutez 13 autres questions pour la catégorie "Jeux-vidéos"
            Question(text = "Quel est le personnage principal de la série de jeux 'The Legend of Zelda'?", choices = listOf("Zelda", "Link", "Ganon", "Epona"), correctAnswer = 1, categoryId = categoryId1),
            Question(text = "Quel jeu de rôle en ligne massivement multijoueur est sorti par Blizzard en 2004?", choices = listOf("World of Warcraft", "EverQuest", "Guild Wars", "Final Fantasy XIV"), correctAnswer = 0, categoryId = categoryId1),
            Question(text = "Dans quel jeu incarne-t-on un chasseur de primes galactique nommé Samus Aran?", choices = listOf("Metroid", "Halo", "Mass Effect", "Destiny"), correctAnswer = 0, categoryId = categoryId1),
            Question(text = "Quel jeu de tir à la première personne est connu pour son mode de jeu 'Battle Royale'?", choices = listOf("Call of Duty", "Fortnite", "Apex Legends", "PUBG"), correctAnswer = 1, categoryId = categoryId1),
            Question(text = "Quel jeu de simulation de vie a été créé par Will Wright?", choices = listOf("The Sims", "SimCity", "Animal Crossing", "Second Life"), correctAnswer = 0, categoryId = categoryId1),
            Question(text = "Quel est le studio de développement derrière la série 'Grand Theft Auto'?", choices = listOf("Rockstar Games", "Electronic Arts", "Ubisoft", "Activision"), correctAnswer = 0, categoryId = categoryId1),
            Question(text = "Quel jeu vidéo est souvent crédité pour avoir sauvé l'industrie du jeu vidéo en 1985?", choices = listOf("Pac-Man", "Donkey Kong", "Super Mario Bros.", "Space Invaders"), correctAnswer = 2, categoryId = categoryId1),
            Question(text = "Quel est le nom de l'intelligence artificielle dans la série 'Halo'?", choices = listOf("Cortana", "GLaDOS", "Siri", "Alexa"), correctAnswer = 0, categoryId = categoryId1),
            Question(text = "Dans quel jeu de puzzle devez-vous faire correspondre des bonbons de différentes couleurs?", choices = listOf("Candy Crush Saga", "Bejeweled", "Tetris", "Puzzle Quest"), correctAnswer = 0, categoryId = categoryId1),
            Question(text = "Quel jeu de plateforme met en vedette un chevalier avec une pelle comme arme principale?", choices = listOf("Shovel Knight", "Hollow Knight", "Knight Quest", "Castle Crashers"), correctAnswer = 0, categoryId = categoryId1),
            Question(text = "Quel est le nom du protagoniste de la série 'Metal Gear'?", choices = listOf("Solid Snake", "Sam Fisher", "Nathan Drake", "Ezio Auditore"), correctAnswer = 0, categoryId = categoryId1),
            Question(text = "Quel jeu de combat a introduit des personnages comme Ryu et Ken?", choices = listOf("Street Fighter", "Tekken", "Mortal Kombat", "Soul Calibur"), correctAnswer = 0, categoryId = categoryId1),
            Question(text = "Quel jeu de stratégie en temps réel se déroule dans l'univers de science-fiction de Koprulu?", choices = listOf("StarCraft", "Warcraft", "Command & Conquer", "Dune II"), correctAnswer = 0, categoryId = categoryId1),

            // Questions pour "Cinéma"
            Question(text = "Quel est le premier film de Quentin Tarantino ?", choices = listOf("Reservoir Dogs", "Pulp Fiction", "Jackie Brown", "Kill Bill"), correctAnswer = 0, categoryId = categoryId2),
            Question(text = "Quel film a remporté l'Oscar du meilleur film en 1994 ?", choices = listOf("Forrest Gump", "Pulp Fiction", "The Shawshank Redemption", "Four Weddings and a Funeral"), correctAnswer = 0, categoryId = categoryId2),
            // Ajoutez 13 autres questions pour la catégorie "Cinéma"
            Question(text = "Quel acteur joue le rôle principal dans 'Fight Club'?", choices = listOf("Brad Pitt", "Edward Norton", "Jared Leto", "Tom Cruise"), correctAnswer = 1, categoryId = categoryId2),
            Question(text = "Quel film d'animation Pixar met en vedette une famille de super-héros?", choices = listOf("The Incredibles", "Toy Story", "Finding Nemo", "Monsters, Inc."), correctAnswer = 0, categoryId = categoryId2),
            Question(text = "Quel réalisateur est connu pour ses films de science-fiction tels que 'Inception' et 'Interstellar'?", choices = listOf("Steven Spielberg", "Christopher Nolan", "Ridley Scott", "James Cameron"), correctAnswer = 1, categoryId = categoryId2),
            Question(text = "Quel film de 1999 est connu pour sa réplique 'I see dead people'?", choices = listOf("The Sixth Sense", "The Others", "The Blair Witch Project", "Stir of Echoes"), correctAnswer = 0, categoryId = categoryId2),
            Question(text = "Quel est le nom de la planète natale de Superman?", choices = listOf("Krypton", "Mars", "Earth", "Zenn-La"), correctAnswer = 0, categoryId = categoryId2),
            Question(text = "Qui a réalisé 'Schindler's List'?", choices = listOf("Steven Spielberg", "Martin Scorsese", "Quentin Tarantino", "Francis Ford Coppola"), correctAnswer = 0, categoryId = categoryId2),
            Question(text = "Quel film a pour personnage principal un robot nommé WALL-E?", choices = listOf("WALL-E", "RoboCop", "Terminator", "Short Circuit"), correctAnswer = 0, categoryId = categoryId2),
            Question(text = "Quel film de 1994 est une adaptation du roman de Stephen King 'Rita Hayworth and Shawshank Redemption'?", choices = listOf("The Shawshank Redemption", "Stand by Me", "The Green Mile", "Misery"), correctAnswer = 0, categoryId = categoryId2),
            Question(text = "Quel acteur a joué le rôle de Jack Dawson dans 'Titanic'?", choices = listOf("Leonardo DiCaprio", "Brad Pitt", "Tom Hanks", "Johnny Depp"), correctAnswer = 0, categoryId = categoryId2),
            Question(text = "Quel film de 2010 met en scène un monde où les rêves peuvent être infiltrés?", choices = listOf("Inception", "Shutter Island", "Avatar", "The Matrix"), correctAnswer = 0, categoryId = categoryId2),
            Question(text = "Quel film d'horreur de 1980 est basé sur un roman de Stephen King et réalisé par Stanley Kubrick?", choices = listOf("The Shining", "Carrie", "It", "Pet Sematary"), correctAnswer = 0, categoryId = categoryId2),
            Question(text = "Quel film met en scène une équipe de scientifiques essayant de capturer un grand requin blanc mangeur d'hommes?", choices = listOf("Jaws", "Deep Blue Sea", "The Meg", "Sharknado"), correctAnswer = 0, categoryId = categoryId2),
            Question(text = "Quel film de 1997 a pour personnage principal un hacker connu sous le pseudonyme de Neo?", choices = listOf("The Matrix", "Johnny Mnemonic", "Hackers", "The Net"), correctAnswer = 0, categoryId = categoryId2),
            Question(text = "Qui a joué le rôle principal dans le film 'Gladiator' sorti en 2000?", choices = listOf("Russell Crowe", "Mel Gibson", "Joaquin Phoenix", "Tommy Flanagan"), correctAnswer = 0, categoryId = categoryId2),

            // Questions pour "Géographie"
            Question(text = "Quelle est la capitale de l'Australie ?", choices = listOf("Sydney", "Melbourne", "Canberra", "Perth"), correctAnswer = 2, categoryId = categoryId3),
            Question(text = "Quel est le plus grand désert du monde ?", choices = listOf("Sahara", "Arctique", "Antarctique", "Gobi"), correctAnswer = 2, categoryId = categoryId3),
            // Ajoutez 13 autres questions pour la catégorie "Géographie"
            Question(text = "Quel est le plus long fleuve du monde?", choices = listOf("Nil", "Amazon", "Yangtsé", "Mississippi"), correctAnswer = 1, categoryId = categoryId3),
            Question(text = "Quelle est la plus grande île du monde?", choices = listOf("Groenland", "Australie", "Madagascar", "Borneo"), correctAnswer = 0, categoryId = categoryId3),
            Question(text = "Quel est le pays le plus peuplé du monde?", choices = listOf("Inde", "Chine", "États-Unis", "Indonésie"), correctAnswer = 1, categoryId = categoryId3),
            Question(text = "Dans quel pays se trouve la ville de Machu Picchu?", choices = listOf("Pérou", "Mexique", "Chili", "Brésil"), correctAnswer = 0, categoryId = categoryId3),
            Question(text = "Quel est le plus grand océan du monde?", choices = listOf("Atlantique", "Indien", "Pacifique", "Arctique"), correctAnswer = 2, categoryId = categoryId3),
            Question(text = "Quel pays a le plus grand nombre de pyramides?", choices = listOf("Égypte", "Soudan", "Mexique", "Chine"), correctAnswer = 1, categoryId = categoryId3),
            Question(text = "Quel est le point culminant de l'Afrique?", choices = listOf("Mont Kilimandjaro", "Mont Kenya", "Mont Elgon", "Mont Meru"), correctAnswer = 0, categoryId = categoryId3),
            Question(text = "Quelle est la capitale du Canada?", choices = listOf("Toronto", "Vancouver", "Montréal", "Ottawa"), correctAnswer = 3, categoryId = categoryId3),
            Question(text = "Quel est le plus petit pays du monde?", choices = listOf("Vatican", "Monaco", "Nauru", "San Marino"), correctAnswer = 0, categoryId = categoryId3),
            Question(text = "Quel pays a la plus longue frontière terrestre avec la France?", choices = listOf("Espagne", "Belgique", "Suisse", "Brésil"), correctAnswer = 3, categoryId = categoryId3),
            Question(text = "Quel est le plus grand lac d'Afrique?", choices = listOf("Lac Victoria", "Lac Tanganyika", "Lac Malawi", "Lac Tchad"), correctAnswer = 0, categoryId = categoryId3),
            Question(text = "Quelle est la capitale de la Norvège?", choices = listOf("Oslo", "Stockholm", "Copenhague", "Helsinki"), correctAnswer = 0, categoryId = categoryId3),
            Question(text = "Quelle est la plus grande ville d'Australie?", choices = listOf("Sydney", "Melbourne", "Brisbane", "Perth"), correctAnswer = 0, categoryId = categoryId3),
            Question(text = "Quel pays est connu pour sa forme en botte?", choices = listOf("Italie", "Grèce", "Croatie", "Portugal"), correctAnswer = 0, categoryId = categoryId3),

            // Questions pour "Histoire"
            Question(text = "Qui était le premier président des États-Unis ?", choices = listOf("Thomas Jefferson", "John Adams", "George Washington", "James Madison"), correctAnswer = 2, categoryId = categoryId4),
            Question(text = "En quelle année a eu lieu la chute du mur de Berlin ?", choices = listOf("1987", "1988", "1989", "1990"), correctAnswer = 2, categoryId = categoryId4),
            // Ajoutez 13 autres questions pour la catégorie "Histoire"
            Question(text = "Quelle civilisation ancienne a construit les pyramides de Gizeh?", choices = listOf("Égyptienne", "Maya", "Inca", "Mésopotamienne"), correctAnswer = 0, categoryId = categoryId4),
            Question(text = "Quel roi de France a été connu sous le nom de 'Roi Soleil'?", choices = listOf("Louis XIV", "Louis XVI", "François Ier", "Henri IV"), correctAnswer = 0, categoryId = categoryId4),
            Question(text = "Quelle guerre a eu lieu entre 1914 et 1918?", choices = listOf("Première Guerre mondiale", "Seconde Guerre mondiale", "Guerre de Corée", "Guerre du Vietnam"), correctAnswer = 0, categoryId = categoryId4),
            Question(text = "Qui a écrit la 'Déclaration d'indépendance' des États-Unis?", choices = listOf("George Washington", "Thomas Jefferson", "Benjamin Franklin", "John Adams"), correctAnswer = 1, categoryId = categoryId4),
            Question(text = "En quelle année Christophe Colomb a-t-il découvert l'Amérique?", choices = listOf("1492", "1500", "1498", "1485"), correctAnswer = 0, categoryId = categoryId4),
            Question(text = "Quel empire était dirigé par Gengis Khan?", choices = listOf("Empire mongol", "Empire ottoman", "Empire romain", "Empire perse"), correctAnswer = 0, categoryId = categoryId4),
            Question(text = "Quelle révolution a eu lieu en France en 1789?", choices = listOf("Révolution française", "Révolution industrielle", "Révolution américaine", "Révolution russe"), correctAnswer = 0, categoryId = categoryId4),
            Question(text = "Qui a été le premier homme à marcher sur la Lune?", choices = listOf("Buzz Aldrin", "Yuri Gagarin", "Neil Armstrong", "Michael Collins"), correctAnswer = 2, categoryId = categoryId4),
            Question(text = "Quelle guerre a opposé les États du Nord et les États du Sud aux États-Unis?", choices = listOf("Guerre civile américaine", "Guerre d'indépendance", "Guerre de Sécession", "Guerre de 1812"), correctAnswer = 2, categoryId = categoryId4),
            Question(text = "Qui a été la première femme à remporter un prix Nobel?", choices = listOf("Marie Curie", "Rosalind Franklin", "Dorothy Hodgkin", "Ada Lovelace"), correctAnswer = 0, categoryId = categoryId4),
            Question(text = "En quelle année le Titanic a-t-il coulé?", choices = listOf("1912", "1905", "1915", "1920"), correctAnswer = 0, categoryId = categoryId4),
            Question(text = "Quel empire était connu pour ses gladiateurs et ses combats dans le Colisée?", choices = listOf("Empire romain", "Empire grec", "Empire égyptien", "Empire perse"), correctAnswer = 0, categoryId = categoryId4),
            Question(text = "Quelle dynastie chinoise est connue pour avoir construit la Grande Muraille de Chine?", choices = listOf("Dynastie Qin", "Dynastie Ming", "Dynastie Han", "Dynastie Tang"), correctAnswer = 0, categoryId = categoryId4),
            Question(text = "Quel traité a mis fin à la Première Guerre mondiale?", choices = listOf("Traité de Versailles", "Traité de Paris", "Traité de Tordesillas", "Traité de Westphalie"), correctAnswer = 0, categoryId = categoryId4),

            // Questions pour "Musique"
            Question(text = "Quel groupe a sorti l'album 'Dark Side of the Moon' ?", choices = listOf("The Beatles", "Pink Floyd", "Led Zeppelin", "The Rolling Stones"), correctAnswer = 1, categoryId = categoryId5),
            Question(text = "Qui est connu comme le roi de la pop ?", choices = listOf("Elvis Presley", "Michael Jackson", "Prince", "Madonna"), correctAnswer = 1, categoryId = categoryId5),
            // Ajoutez 13 autres questions pour la catégorie "Musique"
            Question(text = "Quel artiste a sorti l'album 'Thriller'?", choices = listOf("Michael Jackson", "Prince", "Madonna", "Whitney Houston"), correctAnswer = 0, categoryId = categoryId5),
            Question(text = "Quel groupe britannique est connu pour des chansons comme 'Bohemian Rhapsody' et 'We Will Rock You'?", choices = listOf("The Beatles", "Queen", "Led Zeppelin", "Pink Floyd"), correctAnswer = 1, categoryId = categoryId5),
            Question(text = "Quel instrument de musique a des touches noires et blanches?", choices = listOf("Piano", "Guitare", "Violon", "Flûte"), correctAnswer = 0, categoryId = categoryId5),
            Question(text = "Quel compositeur est connu pour sa symphonie n° 9 en ré mineur?", choices = listOf("Ludwig van Beethoven", "Wolfgang Amadeus Mozart", "Johann Sebastian Bach", "Franz Schubert"), correctAnswer = 0, categoryId = categoryId5),
            Question(text = "Qui est l'auteur-compositeur-interprète de la chanson 'Imagine'?", choices = listOf("John Lennon", "Paul McCartney", "George Harrison", "Ringo Starr"), correctAnswer = 0, categoryId = categoryId5),
            Question(text = "Quel musicien est connu pour ses performances en moonwalk?", choices = listOf("Michael Jackson", "James Brown", "Prince", "David Bowie"), correctAnswer = 0, categoryId = categoryId5),
            Question(text = "Quel groupe est connu pour l'album 'The Wall'?", choices = listOf("Pink Floyd", "The Who", "The Doors", "The Rolling Stones"), correctAnswer = 0, categoryId = categoryId5),
            Question(text = "Quel artiste a sorti l'album 'Lemonade' en 2016?", choices = listOf("Beyoncé", "Rihanna", "Adele", "Taylor Swift"), correctAnswer = 0, categoryId = categoryId5),
            Question(text = "Quel groupe de rock a été formé par Kurt Cobain?", choices = listOf("Nirvana", "Pearl Jam", "Soundgarden", "Alice in Chains"), correctAnswer = 0, categoryId = categoryId5),
            Question(text = "Quel genre musical est associé à Bob Marley?", choices = listOf("Reggae", "Rock", "Blues", "Jazz"), correctAnswer = 0, categoryId = categoryId5),
            Question(text = "Quel artiste est connu pour des tubes comme 'Shape of You' et 'Perfect'?", choices = listOf("Ed Sheeran", "Justin Bieber", "Bruno Mars", "Shawn Mendes"), correctAnswer = 0, categoryId = categoryId5),
            Question(text = "Quel groupe a chanté 'Hey Jude'?", choices = listOf("The Beatles", "The Rolling Stones", "The Beach Boys", "The Who"), correctAnswer = 0, categoryId = categoryId5),
            Question(text = "Quel album est souvent considéré comme le meilleur de Michael Jackson?", choices = listOf("Thriller", "Bad", "Off the Wall", "Dangerous"), correctAnswer = 0, categoryId = categoryId5),
            Question(text = "Quel compositeur est surnommé le 'Roi de la Valse'?", choices = listOf("Johann Strauss II", "Ludwig van Beethoven", "Frédéric Chopin", "Franz Schubert"), correctAnswer = 0, categoryId = categoryId5),

            // Questions pour "Science"
            Question(text = "Quelle est la formule chimique de l'eau ?", choices = listOf("H2O", "CO2", "O2", "N2"), correctAnswer = 0, categoryId = categoryId6),
            Question(text = "Qui a proposé la théorie de la relativité ?", choices = listOf("Isaac Newton", "Galileo Galilei", "Albert Einstein", "Nikola Tesla"), correctAnswer = 2, categoryId = categoryId6),
            // Ajoutez 13 autres questions pour la catégorie "Science"
            Question(text = "Quel est l'élément chimique dont le symbole est 'O'?", choices = listOf("Oxygène", "Or", "Osmium", "Oganesson"), correctAnswer = 0, categoryId = categoryId6),
            Question(text = "Quelle planète est connue comme la planète rouge?", choices = listOf("Mars", "Jupiter", "Saturne", "Vénus"), correctAnswer = 0, categoryId = categoryId6),
            Question(text = "Qui est le père de la théorie de l'évolution?", choices = listOf("Charles Darwin", "Gregor Mendel", "Isaac Newton", "Albert Einstein"), correctAnswer = 0, categoryId = categoryId6),
            Question(text = "Quel type de liaison chimique implique le partage des électrons?", choices = listOf("Liaison covalente", "Liaison ionique", "Liaison métallique", "Liaison hydrogène"), correctAnswer = 0, categoryId = categoryId6),
            Question(text = "Quel est le principal gaz constituant l'atmosphère terrestre?", choices = listOf("Azote", "Oxygène", "Dioxyde de carbone", "Hélium"), correctAnswer = 0, categoryId = categoryId6),
            Question(text = "Quel est le nom scientifique de la peur des araignées?", choices = listOf("Arachnophobie", "Acrophobie", "Claustrophobie", "Hydrophobie"), correctAnswer = 0, categoryId = categoryId6),
            Question(text = "Quel est l'organe le plus grand du corps humain?", choices = listOf("Peau", "Foie", "Cerveau", "Cœur"), correctAnswer = 0, categoryId = categoryId6),
            Question(text = "Quel est le nombre de paires de chromosomes dans le corps humain?", choices = listOf("23", "22", "24", "21"), correctAnswer = 0, categoryId = categoryId6),
            Question(text = "Quelle est la vitesse de la lumière?", choices = listOf("299,792 km/s", "150,000 km/s", "1,080 km/h", "340 m/s"), correctAnswer = 0, categoryId = categoryId6),
            Question(text = "Quel est l'élément chimique le plus léger?", choices = listOf("Hydrogène", "Hélium", "Lithium", "Oxygène"), correctAnswer = 0, categoryId = categoryId6),
            Question(text = "Qui est connu pour sa loi de la gravitation universelle?", choices = listOf("Isaac Newton", "Albert Einstein", "Niels Bohr", "Galileo Galilei"), correctAnswer = 0, categoryId = categoryId6),
            Question(text = "Quel est le pH de l'eau pure?", choices = listOf("7", "6", "8", "9"), correctAnswer = 0, categoryId = categoryId6),
            Question(text = "Quelle force fait tourner une toupie?", choices = listOf("Force centrifuge", "Force centripète", "Force de friction", "Force de gravité"), correctAnswer = 0, categoryId = categoryId6),
            Question(text = "Quelle planète est la plus grande du système solaire?", choices = listOf("Jupiter", "Saturne", "Uranus", "Neptune"), correctAnswer = 0, categoryId = categoryId6)
        )

        // Insérer les questions dans la base de données
        for (question in questions) {
            quizDatabase.questionDao().insertQuestion(question)
        }

        // Log pour vérifier l'insertion des questions
        Log.d("DB_INIT", "Questions inserted")
    }
}
