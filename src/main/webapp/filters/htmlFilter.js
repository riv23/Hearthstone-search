export default function ($sce) {
  return function (input, value) {
    return $sce.trustAsHtml(input);
  }
}
