FROM ubuntu:22.04

RUN apt update
RUN apt install git python -y

RUN git clone https://github.com/Roni1993/.dotfiles
RUN .dotfiles/install

RUN chsh -s /usr/bin/zsh
RUN zsh
RUN zi update
