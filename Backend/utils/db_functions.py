import utils.db as db
from models.category import Category
from models.type import Type
from models.furniture import Furniture
from models.user import User


async def connect_db():
    await db.connect_db()


async def disconnect_db():
    await db.disconnect_db()


async def add_category(category: Category):
    query = "insert into categories values (default, :name, :image) returning id"
    result = await db.execute(query, False, category.dict(exclude={"id"}))
    if result is not None:
        category.id = result


async def add_user(user: User):
    db_user = await get_user(user.phone_number)

    if db_user is None:
        print("is null")

    return db_user


async def get_user(phone_number):
    query = "select * from  users where phone_number=:phone_number"
    return await db.fetch(query, True, values={"phone_number": phone_number})


async def get_categories():
    query = "select * from categories"
    return await db.fetch(query, False)


async def add_type(type: Type):
    query = "insert into types values (default, :name, :image, :category_id) returning id"
    result = await db.execute(query, False, type.dict(exclude={"id"}))
    if result is not None:
        type.id = result


async def get_types(category_id: int = None):
    query = "select * from types"

    parameters = {}
    if category_id is not None:
        query += " where category_id = :category_id"
        parameters["category_id"] = category_id

    return await db.fetch(query, False, values=parameters)


async def add_furniture(furniture: Furniture):
    query = """insert into furniture(id, name, price, image1, image2, image3, model3d, description, type_id) 
            values (default, :name, :price, :image1, :image2, :image3, :model3d, :description, :type_id) returning id"""
    result = await db.execute(query, False, furniture.dict(exclude={"id"}))
    if result is not None:
        furniture.id = result


async def get_furniture(category_id=None, type_id=None):
    query = """select f.id, f.name, f.type_id, f.description, f.price, f.model3d, f.image1, f.image2, f.image3 
    from furniture f left join types t on f.type_id = t.id"""

    parameters = {}

    if category_id is not None:
        query += " where t.category_id = :category_id"
        parameters["category_id"] = category_id

    if type_id is not None:
        if category_id is not None:
            query += " and f.type_id = :type_id"
        else:
            query += " where f.type_id = :type_id"
        parameters["type_id"] = type_id

    return await db.fetch(query, False, values=parameters)
