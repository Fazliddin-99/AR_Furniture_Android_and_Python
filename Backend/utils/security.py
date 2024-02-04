from fastapi import Depends
from fastapi.security import OAuth2PasswordBearer
from passlib.context import CryptContext
from models.jwt_user import JWTUser
from datetime import datetime, timedelta
from utils.constants import JWT_EXPIRATION_TIME_MINUTES, JWT_ALGORITHM, JWT_SECRET_KEY
import jwt
import time

pwd_context = CryptContext(["bcrypt"])
oauth_schema = OAuth2PasswordBearer("/token")


def get_hashed_password(password):
    return pwd_context.hash(password)


def verify_password(plain_password, hashed_password):
    try:
        return pwd_context.verify(plain_password, hashed_password)
    except Exception as e:
        return False


def authenticate_user(user_jwt: JWTUser):
    user = get_user_from_db(user_jwt.username)
    if user is not None:
        if verify_password(user_jwt.password, user.password):
            return user

    return None


def create_jwt_token(user: JWTUser):
    expiration = datetime.utcnow() + timedelta(minutes=JWT_EXPIRATION_TIME_MINUTES)
    jwt_payload = {
        "sub": user.username,
        "exp": expiration,
        "role": user.role
    }

    jwt_token = jwt.encode(jwt_payload, JWT_SECRET_KEY, algorithm=JWT_ALGORITHM)
    return {"token": jwt_token}


def check_jwt_token(token: str = Depends(oauth_schema)):
    try:
        jwt_payload = jwt.decode(token, JWT_SECRET_KEY, algorithms=JWT_ALGORITHM)

        username = jwt_payload.get("sub")
        role = jwt_payload.get("role")
        expiration = jwt_payload.get("exp")

        if time.time() < expiration:
            if get_user_from_db(username) is not None:
                return final_check_jwt_token(role)
    except Exception as e:
        print("error occured while checking jwt token: " + str(e))

    return False


def final_check_jwt_token(role: str):
    if role == "admin":
        return True
    return False


def get_user_from_db(username):
    fake_user = {
        "username": username,
        "password": "$2b$12$/FsXdAeDxbHq9..vCWjauOnvoXYJi0btBW6lv6y40h/VHYAyfnO9e",  # admin123
        "disabled": False,
        "role": "admin"
    }
    return JWTUser(**fake_user)
