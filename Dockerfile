FROM ubuntu:22.04

USER ROOT

RUN apt update
RUN apt install git python3 -y

WORKDIR /root
RUN git clone https://github.com/Roni1993/.dotfiles
RUN .dotfiles/install

RUN chsh -s /usr/bin/zsh
