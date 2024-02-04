from fastapi import FastAPI, Depends, HTTPException
from typing import Optional
import uvicorn
from starlette.requests import Request
from starlette.responses import Response
import utils.db_functions as db
from models.category import Category
from models.jwt_user import JWTUser
from models.type import Type
from models.furniture import Furniture
from models.user import User
from fastapi.security import OAuth2PasswordRequestForm
from utils.security import authenticate_user, create_jwt_token
from starlette.status import HTTP_401_UNAUTHORIZED
from utils.constants import API_KEY

app = FastAPI()

# if not any(word in str(request.url) for word in ["/token", "/docs", "/openapi.json"]):
#     try:
#         token = request.headers["Authorization"].split(" ")[1]
#         is_valid = check_jwt_token(token)
#     except:
#         is_valid = False
#
#     if not is_valid:
#         return Response("Unauthorized", status_code=HTTP_401_UNAUTHORIZED)

if __name__ == "__main__":
    uvicorn.run("main:app")


@app.on_event("startup")
async def connect_db():
    await db.connect_db()


@app.on_event("shutdown")
async def disconnect_db():
    await db.disconnect_db()


@app.middleware("http")
async def middleware(request: Request, call_next):
    try:
        api_key = request.headers["x-key"]
        if api_key != API_KEY:
            return Response("Unauthorized", status_code=HTTP_401_UNAUTHORIZED)
    except:
        return Response("Unauthorized", status_code=HTTP_401_UNAUTHORIZED)

    response = await call_next(request)
    return response


@app.get("/")
async def root():
    return "It's working"


@app.post("/user")
async def create_user(user: User):
    result = await db.add_user(user)
    return result


@app.post("/category")
async def add_category(category: Category):
    await db.add_category(category)
    return {"result": category}


@app.get("/categories")
async def get_categories():
    return await db.get_categories()


@app.post("/type")
async def add_type(type_furniture: Type):
    await db.add_type(type_furniture)
    return {"result": type_furniture}


@app.get("/types")
async def get_types(category_id: Optional[int] = None):
    return await db.get_types(category_id)


@app.get("/furniture")
async def get_furniture(category_id: Optional[int] = None, type_id: Optional[int] = None):
    return await db.get_furniture(category_id, type_id)


@app.post("/furniture")
async def add_furniture(furniture: Furniture):
    await db.add_furniture(furniture)
    return {"result": furniture}


@app.post("/token")
async def login_for_access_token(form_data: OAuth2PasswordRequestForm = Depends()):
    jwt_user_dict = {
        "username": form_data.username,
        "password": form_data.password
    }

    jwt_user = JWTUser(**jwt_user_dict)

    user = authenticate_user(jwt_user)

    if user is None:
        raise HTTPException(status_code=HTTP_401_UNAUTHORIZED)

    return create_jwt_token(user)
