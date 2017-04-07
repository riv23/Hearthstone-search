export default function test ($sce) {
  return function (input, value) {
    return $sce.trustAsHtml(input);
  }
}

test.$inject = ["$sce"];
