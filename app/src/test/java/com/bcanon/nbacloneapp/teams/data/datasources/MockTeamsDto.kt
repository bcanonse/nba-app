package com.bcanon.nbacloneapp.teams.data.datasources

val getTeams = """
    {
        "data": [
            {
                "id": 1,
                "abbreviation": "ATL",
                "city": "Atlanta",
                "conference": "East",
                "division": "Southeast",
                "full_name": "Atlanta Hawks",
                "name": "Hawks"
            },
            {
                "id": 2,
                "abbreviation": "BOS",
                "city": "Boston",
                "conference": "East",
                "division": "Atlantic",
                "full_name": "Boston Celtics",
                "name": "Celtics"
            },
            {
                "id": 3,
                "abbreviation": "BKN",
                "city": "Brooklyn",
                "conference": "East",
                "division": "Atlantic",
                "full_name": "Brooklyn Nets",
                "name": "Nets"
            },
            {
                "id": 4,
                "abbreviation": "CHA",
                "city": "Charlotte",
                "conference": "East",
                "division": "Southeast",
                "full_name": "Charlotte Hornets",
                "name": "Hornets"
            }
        ],
        "meta": {
            "total_pages": 1,
            "current_page": 1,
            "next_page": null,
            "per_page": 30,
            "total_count": 30
        }
    }
""".trimIndent()

val getTeamsEmpty = """
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