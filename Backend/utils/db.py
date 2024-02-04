from databases import Database
from utils.constants import DB_HOST, DB_NAME, DB_USER, DB_PASSWORD

db = Database(f"postgresql://{DB_USER}:{DB_PASSWORD}@{DB_HOST}/{DB_NAME}")


async def connect_db():
    await db.connect()


async def disconnect_db():
    await db.disconnect()


async def execute(query, is_many, values=None):
    if is_many:
        result = await db.execute_many(query, values)
    else:
        result = await db.execute(query, values)
    return result


async def fetch(query, is_one, values=None):
    if is_one:
        result = await db.fetch_one(query, values)

        if result is None:
            out = None
        else:
            out = dict(result)
    else:
        result = await db.fetch_all(query, values)
        if result is None:
            out = None
        else:
            out = []
            for row in result:
                out.append(dict(row))
    return out
