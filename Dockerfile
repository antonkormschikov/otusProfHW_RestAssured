from maven:3

RUN "mkdir -p /home/ubuntu/api_tests"
WORKDIR "/home/ubuntu/api_tests"

ENTRYPOINT ["entrypoint.sh"]