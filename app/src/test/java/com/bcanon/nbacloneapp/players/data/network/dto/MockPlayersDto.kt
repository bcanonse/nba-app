package com.bcanon.nbacloneapp.players.data.network.dto

val playersDto = PlayersDto(
    id = 1L,
    firstName = "Lebron",
    lastName = "James",
    heightFeet = 80,
    heightInches = 80,
    position = "F",
    team = Team(1, "Lakers", "Los Angeles", "LA", "", "", ""), weighPounds = 80
)

val mockListPlayersDto = listOf(playersDto, playersDto)

val getPlayers = """
    {
        "data": [
            {
                "id": 881,
                "first_name": "Eddie",
                "height_feet": null,
                "height_inches": null,
                "last_name": "Jones",
                "position": "",
                "team": {
                    "id": 14,
                    "abbreviation": "LAL",
                    "city": "Los Angeles",
                    "conference": "West",
                    "division": "Pacific",
                    "full_name": "Los Angeles Lakers",
                    "name": "Lakers"
                },
                "weight_pounds": null
            },
            {
                "id": 1052,
                "first_name": "Dontae'",
                "height_feet": null,
                "height_inches": null,
                "last_name": "Jones",
                "position": "",
                "team": {
                    "id": 20,
                    "abbreviation": "NYK",
                    "city": "New York",
                    "conference": "East",
                    "division": "Atlantic",
                    "full_name": "New York Knicks",
                    "name": "Knicks"
                },
                "weight_pounds": null
            },
            {
                "id": 1203,
                "first_name": "Damon",
                "height_feet": null,
                "height_inches": null,
                "last_name": "Jones",
                "position": "",
                "team": {
                    "id": 3,
                    "abbreviation": "BKN",
                    "city": "Brooklyn",
                    "conference": "East",
                    "division": "Atlantic",
                    "full_name": "Brooklyn Nets",
                    "name": "Nets"
                },
                "weight_pounds": null
            }
        ],
        "meta": {
            "total_pages": 16,
            "current_page": 2,
            "next_page": 3,
            "per_page": 3,
            "total_count": 47
        }
    }
""".trimIndent()


val getPlayersEmpty = """
    {
        "data": [],
        "meta": {
            "total_pages": 1,
            "current_page": 2,
            "next_page": null,
            "per_page": 30,
            "total_count": 30
        }
    }
""".trimIndent()