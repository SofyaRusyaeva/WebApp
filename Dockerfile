FROM postgres:latest
ENV POSTGRES_DB=shop_database
ENV POSTGRES_USER=shop_user
ENV POSTGRES_PASSWORD=shop_password
EXPOSE 5432